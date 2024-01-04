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
@Table(name = "read_list", schema = "pap", catalog = "postgres")
public class ReadList implements java.io.Serializable{
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReadList)) return false;
        ReadList readList = (ReadList) o;
        return getReadListId() == readList.getReadListId() && getUserId() == readList.getUserId() && getBookId() == readList.getBookId() && getDateAdded().equals(readList.getDateAdded());
    }

    @Override
    public int hashCode() {
        int result = getReadListId();
        result = 31 * result + getUserId();
        result = 31 * result + getBookId();
        result = 31 * result + getDateAdded().hashCode();
        return result;
    }
}
