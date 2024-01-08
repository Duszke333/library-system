package pap.helpers;

import pap.db.Entities.*;
import pap.db.Repository.BookRepository;
import pap.db.Repository.RentalRepository;
import pap.db.Repository.ReportRepository;
import pap.db.Repository.UserRepository;

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
}
