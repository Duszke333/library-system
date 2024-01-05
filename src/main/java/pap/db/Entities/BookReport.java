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
@Setter
@Getter
@Table(name = "reports", schema = "pap", catalog = "pap")
public class BookReport {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "report_id")
    private int reportId;
    @Basic
    @Column(name = "book_id")
    private int bookId;
    @Basic
    @Column(name = "user_id")
    private int userId;
    @Basic
    @Column(name = "report_type")
    @Enumerated(EnumType.STRING)
    private ReportType reportType;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "report_date")
    private Date reportDate;
    @Basic
    @Column(name = "resolved")
    boolean resolved;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookReport)) return false;
        BookReport report = (BookReport) o;
        return getReportId() == report.getReportId() &&
                getUserId() == report.getUserId() &&
                getBookId() == report.getBookId() &&
                getReportDate().equals(report.getReportDate());
    }

    public enum ReportType{
        DAMAGE,
        LOSS
    }


}



