package pap.helpers;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import pap.db.Entities.Book;
import pap.db.Entities.BookRental;
import pap.db.Entities.BookReport;
import pap.db.Entities.Penalty;
import pap.db.Repository.BookRepository;
import pap.db.Repository.RentalRepository;
import pap.db.Repository.ReportRepository;
import pap.db.Repository.UserRepository;

import java.util.Date;
import java.util.List;

@Getter
public class PenaltyRecord {
    /**
     * A class that represents a record of the TableView in browse-penalties page.
     */
    private int penaltyId;
    private int userId;
    private int rentalId;
    private int bookId;
    private String title;
    private String author;
    private String dateAdded;
    private String datePaid;
    private Boolean paid;
    private Double amount;
    private String cause;

    public PenaltyRecord(Penalty penalty) {
        this.penaltyId = penalty.getPenaltyId();
        this.userId = penalty.getUserId();
        this.rentalId = penalty.getRentalId();
        this.dateAdded = penalty.getDateAdded().toString();
        BookRental rental = new pap.db.Repository.RentalRepository().getById(rentalId);
        Book book = new BookRepository().getById(rental.getBookId());
        this.bookId = book.getBookId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        if (penalty.getDatePaid() != null) {
            this.paid = true;
            this.datePaid = penalty.getDatePaid().toString();
        } else {
            this.paid = false;
            this.datePaid = "";
        }
        this.amount = penalty.getAmount();
        this.cause = penalty.getCause();
    }

    public static List<PenaltyRecord> getUserPenalties(int userId) {
        List<Penalty> penalties = new RentalRepository().getPenaltiesByUserId(userId);
        List<PenaltyRecord> records = new java.util.ArrayList<>();
        for (Penalty penalty : penalties) {
            records.add(new PenaltyRecord(penalty));
        }
        return records;
    }
}