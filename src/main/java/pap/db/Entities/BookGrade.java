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
@Table(name = "book_grades", schema = "pap", catalog = "pap")
public class BookGrade {
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
}
