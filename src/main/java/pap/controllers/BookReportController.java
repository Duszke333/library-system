package pap.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import lombok.Setter;
import pap.db.DAO.EntityDAO.BookReportDAO;
import pap.db.Entities.Book;
import pap.db.Entities.BookReport;
import pap.helpers.LoadedPages;
import pap.helpers.PenaltyManager;


import java.net.URL;
import java.util.ResourceBundle;

public class BookReportController implements UpdatableController, Initializable {
    /**
     * A controller class for book-report page which allows a user to report an issue with a book.
     */
    @FXML
    private Button cancelButton;
    @FXML
    private Button confirmButton;
    @FXML
    private Label reportLabel;
    @FXML
    private TextArea reportText;
    @FXML
    private ChoiceBox<BookReport.ReportType> reportBox;
    @Setter
    static Book book = new Book(0, "isbn", "title", "author", "genre", 0, "language", 0, "publisher", Book.BookStatus.Available, "description", new java.sql.Date(System.currentTimeMillis()), "cover");
    @Setter
    static int userId;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        update();
    }

    /**
     * A method that asks the user if he is certain that he wants to cancel issue reporting
     * and returns to the previous page if so.
     */
    @FXML
    void cancelButtonClicked(MouseEvent event) {
        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Are you certain you wish to cancel? Any unsaved changes will be discarded.",
                ButtonType.YES,
                ButtonType.NO
        );
        alert.setHeaderText(null);
        var result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            GlobalController.switchVisibleContent(LoadedPages.userDashboard);
        } else {
            alert.close();
        }
    }

    /**
     * A method that asks the user if he wants to confirm the report and reports the book if so.
     */
    @FXML
    void confirmButtonClicked(MouseEvent event) {
        boolean isReportBoxFilled = reportBox.getValue() != null;
        boolean isReportTextFilled = !reportText.getText().trim().isEmpty();
        if (isReportBoxFilled && isReportTextFilled) {
            // ask user if he is sure he wants to report the book
            Alert alert = new Alert(
                    Alert.AlertType.WARNING,
                    "This action is irreversible and may result in penalties or fines.",
                    ButtonType.OK,
                    ButtonType.CANCEL
            );
            alert.setHeaderText("Warning: Irreversible Action!");
            var result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // create new report and add it to the database
                BookReport report = new BookReport();
                BookReportDAO bookReportDAO = new BookReportDAO();
                report.setBookId(book.getBookId());
                report.setUserId(userId);
                report.setDescription(reportText.getText());
                report.setReportType(reportBox.getValue());
                report.setReportDate(new java.sql.Date(System.currentTimeMillis()));
                bookReportDAO.create(report);

                // deactivate the book
                PenaltyManager.deactivateBook(book.getBookId());

                // go to the user dashboard
                GlobalController.switchVisibleContent(LoadedPages.userDashboard);
            } else {
                alert.close();
            }
        } else {
            // if there are some values missing, show an error message
            String errorMessage = "";
            if (!isReportBoxFilled) {
                errorMessage += "Please select a report type.\n";
            }
            if (!isReportTextFilled) {
                errorMessage += "Please fill in the report text.\n";
            }
            Alert errorAlert = new Alert(
                    Alert.AlertType.ERROR,
                    errorMessage,
                    ButtonType.OK
            );
            errorAlert.setHeaderText("Error: Incomplete Information!");
            errorAlert.showAndWait();
        }
    }
    @Override
    public void update() {
        reportBox.getItems().clear();
        reportBox.getItems().addAll(BookReport.ReportType.values());
    }
}
