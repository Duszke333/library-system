package pap.db.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "book_grades", schema = "pap", catalog = "postgres")
public class BookGrade implements java.io.Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grade_id", nullable = false)
    private int gradeId;
    @Basic
    @Column(name = "book_id")
    private int bookId;
    @Basic
    @Column(name = "user_id")
    private int userId;
    @Basic
    @Column(name = "grade")
    private double grade;
    @Basic
    @Column(name = "date_added")
    private Date dateAdded;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookGrade)) return false;
        BookGrade bookGrade = (BookGrade) o;
        return getGradeId() == bookGrade.getGradeId() && getBookId() == bookGrade.getBookId() && getUserId() == bookGrade.getUserId() && Double.compare(bookGrade.getGrade(), getGrade()) == 0 && getDateAdded().equals(bookGrade.getDateAdded());
    }

    @Override
    public int hashCode() {
        int result = getGradeId();
        result = 31 * result + getBookId();
        result = 31 * result + getUserId();
        result = 31 * result + (getGrade() != +0.0d ? Double.hashCode(getGrade()) : 0);
        result = 31 * result + getDateAdded().hashCode();
        return result;
    }
}
