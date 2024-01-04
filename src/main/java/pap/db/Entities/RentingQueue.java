package pap.db.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "renting_queue", schema = "pap", catalog = "postgres")
public class RentingQueue implements java.io.Serializable{
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RentingQueue)) return false;
        RentingQueue rentingQueue = (RentingQueue) o;
        return getQueueId() == rentingQueue.getQueueId() && getBookId() == rentingQueue.getBookId() && getUserId() == rentingQueue.getUserId() && getDateToRent().equals(rentingQueue.getDateToRent()) && getDateToReturn().equals(rentingQueue.getDateToReturn());
    }

    @Override
    public int hashCode() {
        int result = getQueueId();
        result = 31 * result + getBookId();
        result = 31 * result + getUserId();
        result = 31 * result + getDateToRent().hashCode();
        result = 31 * result + getDateToReturn().hashCode();
        return result;
    }
}
