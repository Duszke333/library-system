package pap.db.Entities;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "read_list", schema = "pap", catalog = "pap")
public class ReadList {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "read_list_id")
    private int readListId;
    @Basic
    @Column(name = "user_id")
    private int userId;
    @Basic
    @Column(name = "book_id")
    private int bookId;
    @Basic
    @Column(name = "date_added")
    private Date dateAdded;

    public int getReadListId() {
        return readListId;
    }

    public void setReadListId(int readListId) {
        this.readListId = readListId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }
}
