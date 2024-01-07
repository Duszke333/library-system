package pap.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import lombok.Getter;
import lombok.Setter;
import pap.db.Entities.BookReport;
import pap.db.Repository.ReportRepository;
import pap.helpers.IssueRecord;
import pap.helpers.LoadedPages;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class EmployeeShowIssueController implements UpdatableController, Initializable {

    @FXML
    private Label dateReported;
    @FXML
    private Label authorLabel;
    @FXML
    private Label reportType;
    @FXML
    private Label userId;
    @FXML
    private Label userFirstName;
    @FXML
    private Label userLastName;
    @FXML
    private Label bookId;
    @FXML
    private Label titleLabel;
    @FXML
    private Label author;
    @FXML
    private TextArea descriptionLabel;
    @FXML
    private Button confirmButton;
    @FXML
    private Button cancelButton;
    @Setter
    @Getter
    static IssueRecord selectedIssueRecord =new IssueRecord(new BookReport(1, 1, 1, BookReport.ReportType.LOSS, "", new java.sql.Date(System.currentTimeMillis()), false
    ));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        update();
    }

    @Override
    public void update(){
        dateReported.setText("Date Reported: " + selectedIssueRecord.getDateReported());
        authorLabel.setText("Author: " + selectedIssueRecord.getAuthor());
        reportType.setText("Report Type: " + selectedIssueRecord.getReportType());
        userId.setText("User ID: " + selectedIssueRecord.getUserId());
        userFirstName.setText("User's First Name: " + selectedIssueRecord.getUserFirstName());
        userLastName.setText("User's Last Name: " + selectedIssueRecord.getUserLastName());
        bookId.setText("Book ID: " + selectedIssueRecord.getBookId());
        titleLabel.setText("Title: " + selectedIssueRecord.getTitle());
        author.setText("Author: " + selectedIssueRecord.getAuthor());
        descriptionLabel.setText("Description: " + selectedIssueRecord.getDescription());
    }

    @FXML
    void cancelButtonClicked(MouseEvent event) {
        Alert alert = new Alert(
                Alert.AlertType.WARNING,
                "Are you sure you want to cancel? The issue won't be resolved.",
                ButtonType.YES,
                ButtonType.NO
        );
        alert.setHeaderText("Warning: Cancelling the resolution!");
        var result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            GlobalController.switchVisibleContent(LoadedPages.employeeIssueManage);
        } else {
            alert.close();
        }
    }


    @FXML
    void confirmButtonClicked(MouseEvent event) {
        Alert alert = new Alert(
                Alert.AlertType.WARNING,
                "This action is irreversible and will mark this issue as resolved.",
                ButtonType.OK,
                ButtonType.CANCEL
        );
        alert.setHeaderText("Warning: Irreversible Action!");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            BookReport report = new ReportRepository().getById(selectedIssueRecord.getReportId());
            new ReportRepository().delete(report);
            GlobalController.switchVisibleContent(LoadedPages.employeeIssueManage);
        }
    }
}
