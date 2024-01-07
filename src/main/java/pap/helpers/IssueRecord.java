package pap.helpers;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import pap.db.Entities.BookReport;
import pap.db.Repository.BookRepository;
import pap.db.Repository.ReportRepository;
import pap.db.Repository.UserRepository;

import java.util.Date;
import java.util.List;


public class IssueRecord {
    @Getter
    private int reportId;
    @Getter
    private int bookId;
    @Getter
    private int userId;
    @Getter
    private String title;
    @Getter
    private String author;
    @Getter
    private String description;
    @Getter
    private String userFirstName;
    @Getter
    private String userLastName;
    @Getter
    @Enumerated(EnumType.STRING)
    private BookReport.ReportType reportType;
    @Getter
    private Date dateReported;
    @Getter
    private Boolean resolved;

    public IssueRecord(BookReport report) {
        this.reportId = report.getReportId();
        this.bookId = report.getBookId();
        this.userId = report.getUserId();
        this.userFirstName = new UserRepository().getById(report.getUserId()).getFirstName();
        this.userLastName = new UserRepository().getById(report.getUserId()).getLastName();
        this.author = new BookRepository().getById(bookId).getAuthor();
        this.title = new BookRepository().getById(bookId).getTitle();
        this.description = report.getDescription();
        this.reportType = report.getReportType();
        this.dateReported = report.getReportDate();
        this.resolved = report.isResolved();
    }

    public static List<IssueRecord> getAll() {
        List<BookReport> reports = new ReportRepository().getAll();
        List<IssueRecord> records = new java.util.ArrayList<>();
        for (BookReport report : reports) {
            records.add(new IssueRecord(report));
        }
        return records;
    }

}