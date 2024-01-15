package pap.helpers;

import lombok.Getter;
import pap.db.Entities.Book;
import pap.db.Entities.BookRental;
import pap.db.Entities.RentingQueue;
import pap.db.Entities.User;
import pap.db.Repository.BookRepository;
import pap.db.Repository.RentalRepository;
import pap.db.Repository.UserRepository;

import java.util.List;

@Getter
public class QueueRecord {
    /**
     * A class that represents a record of the TableView in browse-queues page.
     */
    private int queueId;
    private int bookId;
    private int userId;
    private String date;
    private String title;
    private String author;
    private String language;
    private Boolean ready;

    public QueueRecord(RentingQueue queue) {
        this.queueId = queue.getQueueId();
        this.bookId = queue.getBookId();
        this.userId = queue.getUserId();
        Book book = new BookRepository().getById(bookId);
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.language = book.getLanguage();
        this.date = queue.getDateToRent().toString();
        if (book.getStatus().equals(Book.BookStatus.ReadyForPickup) &&
        new RentalRepository().getRentingQueuesByBookId(bookId).get(0).getUserId() == userId) {
            this.ready = true;
        } else {
            this.ready = false;
        }
    }

    public static List<QueueRecord> getUserQueues(int userId) {
        List<RentingQueue> raw = new RentalRepository().getRentingQueuesByUserId(userId);
        List<QueueRecord> records = new java.util.ArrayList<>();
        for (RentingQueue queue : raw) {
            records.add(new QueueRecord(queue));
        }
        return records;
    }
}