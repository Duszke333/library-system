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


@Getter
public class IssueRecord {
    /**
     * A class that represents a record of the TableView in employee-issue-manage page.
     */
    private int reportId;
    private int bookId;
    private int userId;
    private String title;
    private String author;
    private String description;
    private String userFirstName;
    private String userLastName;
    @Enumerated(EnumType.STRING)
    private BookReport.ReportType reportType;
    private Date dateReported;
    private Boolean resolved;

    /**
     * Constructor for the IssueRecord class.
     * @param report BookReport object that is used to create the record.
     */
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

    /**
     * A method that returns a list of all book reports.
     * @return list of all book reports
     */
    public static List<IssueRecord> getAll() {
        // get all the book reports
        List<BookReport> reports = new ReportRepository().getAll();

        // create a list of records from the book reports
        List<IssueRecord> records = new java.util.ArrayList<>();
        for (BookReport report : reports) {
            records.add(new IssueRecord(report));
        }
        return records;
    }

}