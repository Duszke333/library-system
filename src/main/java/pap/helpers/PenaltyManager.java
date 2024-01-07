package pap.helpers;

import pap.db.Entities.*;
import pap.db.Repository.BookRepository;
import pap.db.Repository.RentalRepository;
import pap.db.Repository.ReportRepository;
import pap.db.Repository.UserRepository;

public class PenaltyManager {
    public static void createReportPenalty(int reportId) {
        ReportRepository reportRepo = new ReportRepository();
        BookReport report = reportRepo.getById(reportId);
        Penalty penalty = new Penalty();
        penalty.setUserId(report.getUserId());
        var date = new java.sql.Date(System.currentTimeMillis());
        penalty.setDateAdded(date);
        penalty.setDateUpdated(date);

        // Update book status to unavailable (will be fixed when penalty is paid)
        int bookId = report.getBookId();
        BookRepository bookRepo = new BookRepository();
        var book = bookRepo.getById(bookId);
        book.setStatus(Book.BookStatus.Unavailable);
        bookRepo.update(book);

        // End current rental
        RentalRepository repo = new RentalRepository();
        BookRental currentRental = repo.getCurrentBookRental(bookId);
        penalty.setRentalId(currentRental.getRentalId());
        currentRental.setDateReturned(date);
        repo.update(currentRental);

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
        repo.createPenalty(penalty);
    }
}
