package pap.helpers;

import lombok.Getter;
import pap.db.Entities.Book;
import pap.db.Entities.BookRental;
import pap.db.Entities.User;
import pap.db.Repository.BookRepository;
import pap.db.Repository.RentalRepository;
import pap.db.Repository.UserRepository;

import java.util.List;

public class RentalRecord {
    @Getter
    private int rentalId;
    @Getter
    private int bookId;
    @Getter
    private int userId;
    @Getter
    private String dateRented;
    @Getter
    private String dateToReturn;
    @Getter
    private String dateReturned;
    @Getter
    private String title;
    @Getter
    private String author;
    @Getter
    private String language;
    @Getter
    private String userFirstName;
    @Getter
    private String userLastName;

    public RentalRecord(BookRental rental) {
        this.rentalId = rental.getRentalId();
        this.bookId = rental.getBookId();
        this.userId = rental.getUserId();
        this.dateRented = rental.getDateRented().toString();
        this.dateToReturn = rental.getDateToReturn().toString();
        if (rental.getDateReturned() != null) {
            this.dateReturned = rental.getDateReturned().toString();
        }
        Book book = new BookRepository().getById(bookId);
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.language = book.getLanguage();

        User user = new UserRepository().getById(userId);
        this.userFirstName = user.getFirstName();
        this.userLastName = user.getLastName();
    }

    public static List<RentalRecord> getCurrentlyRented(int userId) {
        List<BookRental> raw = new RentalRepository().getRentalsByUserId(userId);
        List<RentalRecord> records = new java.util.ArrayList<>();
        for (BookRental rental : raw) {
            RentalRecord record = new RentalRecord(rental);
            if (record.getDateReturned() == null) {
                records.add(record);
            }
        }
        return records;
    }

    public static List<RentalRecord> getRentalHistory(int userId) {
        List<BookRental> raw = new RentalRepository().getRentalsByUserId(userId);
        List<RentalRecord> records = new java.util.ArrayList<>();
        for (BookRental rental : raw) {
            RentalRecord record = new RentalRecord(rental);
            if (record.getDateReturned() != null) {
                records.add(record);
            }
        }
        return records;
    }

    public static List<RentalRecord> getBookRentalHistory(int bookId) {
        List<BookRental> raw = new RentalRepository().getRentalsByBookId(bookId);
        List<RentalRecord> records = new java.util.ArrayList<>();
        for (BookRental rental : raw) {
            records.add(new RentalRecord(rental));
        }
        return records;
    }
}