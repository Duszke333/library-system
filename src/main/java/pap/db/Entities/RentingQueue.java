package pap.db.Entities;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "renting_queue", schema = "pap", catalog = "pap")
public class RentingQueue {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "queue_id")
    private int queueId;
    @Basic
    @Column(name = "book_id")
    private int bookId;
    @Basic
    @Column(name = "user_id")
    private int userId;
    @Basic
    @Column(name = "date_to_rent")
    private Date dateToRent;
    @Basic
    @Column(name = "date_to_return")
    private Date dateToReturn;

    public int getQueueId() {
        return queueId;
    }

    public void setQueueId(int queueId) {
        this.queueId = queueId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getDateToRent() {
        return dateToRent;
    }

    public void setDateToRent(Date dateToRent) {
        this.dateToRent = dateToRent;
    }

    public Date getDateToReturn() {
        return dateToReturn;
    }

    public void setDateToReturn(Date dateToReturn) {
        this.dateToReturn = dateToReturn;
    }
}
