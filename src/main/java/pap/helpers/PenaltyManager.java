package pap.helpers;

import pap.db.Entities.*;
import pap.db.Repository.BookRepository;
import pap.db.Repository.RentalRepository;
import pap.db.Repository.ReportRepository;
import pap.db.Repository.UserRepository;

import java.time.temporal.ChronoUnit;
import java.util.List;

public class PenaltyManager {
    public static void createReportPenalty(int reportId) {
        ReportRepository reportRepo = new ReportRepository();
        BookReport report = reportRepo.getById(reportId);
        Penalty penalty = new Penalty();
        penalty.setUserId(report.getUserId());
        var date = new java.sql.Date(System.currentTimeMillis());
        penalty.setDateAdded(date);
        penalty.setDateUpdated(date);
        BookRental rental = new RentalRepository().getLastBookRental(report.getBookId());
        penalty.setRentalId(rental.getRentalId());

        // Set penalty amount
        if (report.getReportType().equals(BookReport.ReportType.LOSS)) {
            penalty.setCause(Penalty.PenaltyCause.Lost);
            penalty.setAmount(Parameters.getBookLossPenalty());
        } else {
            penalty.setCause(Penalty.PenaltyCause.Damaged);
            penalty.setAmount(Parameters.getBookDamagePenalty());
        }

        // prevent user from renting books until penalty is paid
        UserRepository userRepo = new UserRepository();
        User user = userRepo.getById(report.getUserId());
        user.setHasUnpaidPenalty(true);
        userRepo.update(user);

        // delete user from queues
        removeUserFromQueues(report.getUserId());

        // create penalty
        new RentalRepository().createPenalty(penalty);
    }

    public static void deactivateBook(int bookId) {
        // set book status to unavailable
        BookRepository bookRepo = new BookRepository();
        Book book = bookRepo.getById(bookId);
        book.setStatus(Book.BookStatus.Unavailable);
        bookRepo.update(book);

        // end current rental
        RentalRepository repo = new RentalRepository();
        BookRental currentRental = repo.getCurrentBookRental(bookId);
        currentRental.setDateReturned(new java.sql.Date(System.currentTimeMillis()));
        repo.update(currentRental);

        // delete queue for this book
        List<RentingQueue> queue = repo.getRentingQueuesByBookId(bookId);
        for (RentingQueue q : queue) {
            repo.deleteRentingQueue(q);
        }
    }

    public static void removeUserFromQueues(int userId) {
        RentalRepository repo = new RentalRepository();
        List<RentingQueue> queue = repo.getRentingQueuesByUserId(userId);
        for (RentingQueue q : queue) {
            int bookId = q.getBookId();
            BookRepository bookRepo = new BookRepository();
            List<RentingQueue> bookQueue = repo.getRentingQueuesByBookId(bookId);
            if (bookQueue.size() == 1) {
                // delete entry
                repo.deleteRentingQueue(q);
                // update book status
                Book book = bookRepo.getById(bookId);
                if (book.getStatus().equals(Book.BookStatus.ReadyForPickup)) {
                    book.setStatus(Book.BookStatus.Available);
                } else {
                    book.setStatus(Book.BookStatus.Rented);
                }
                bookRepo.update(book);
                continue;
            }
            // Shift everyone in queue
            RentingQueue previous = null;
            for (RentingQueue entry : bookQueue) {
                if (entry.getUserId() == userId) {
                    previous = entry;
                    continue;
                }
                if (previous != null) {
                    int uid = entry.getUserId();
                    previous.setUserId(uid);
                    repo.updateRentingQueue(previous);
                    previous = entry;
                }
            }
            // Delete last entry
            repo.deleteRentingQueue(previous);
        }
    }

    public static void checkLateReturns() {
        RentalRepository repo = new RentalRepository();
        List<BookRental> rentals = repo.getAllExceededRentals();
        for (BookRental rental : rentals) {
            Penalty penalty = repo.getPenaltyByRentalId(rental.getRentalId());
            if (penalty == null) {
                // create penalty
                int uid = rental.getUserId();
                penalty = new Penalty();
                penalty.setUserId(uid);
                penalty.setRentalId(rental.getRentalId());
                penalty.setCause(Penalty.PenaltyCause.Late);
                penalty.setDateAdded(new java.sql.Date(System.currentTimeMillis()));
                penalty.setDateUpdated(new java.sql.Date(System.currentTimeMillis()));

                java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
                long diff = ChronoUnit.DAYS.between(rental.getDateToReturn().toLocalDate(), date.toLocalDate());

                penalty.setAmount(diff * Parameters.getDailyPenalty());
                repo.createPenalty(penalty);

                // update user status
                UserRepository userRepo = new UserRepository();
                User user = userRepo.getById(uid);
                user.setHasUnpaidPenalty(true);
                userRepo.update(user);

                // delete user from queues
                removeUserFromQueues(uid);

            } else {
                java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
                // Check how many days have passed since last update
                int currentDayOfTheYear = date.toLocalDate().getDayOfYear();
                int lastUpdateDayOfTheYear = penalty.getDateUpdated().toLocalDate().getDayOfYear();
                if (lastUpdateDayOfTheYear < currentDayOfTheYear) {
                    // Update penalty accordingly
                    long diff = ChronoUnit.DAYS.between(penalty.getDateUpdated().toLocalDate(), date.toLocalDate());
                    penalty.setAmount(penalty.getAmount() + diff * Parameters.getDailyPenalty());
                    penalty.setDateUpdated(date);
                    repo.updatePenalty(penalty);
                }
            }
        }
    }
}
