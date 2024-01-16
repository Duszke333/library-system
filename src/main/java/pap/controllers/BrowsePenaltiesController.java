package pap.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import pap.db.Entities.Book;
import pap.db.Entities.BookRental;
import pap.db.Entities.Penalty;
import pap.db.Entities.User;
import pap.db.Repository.BookRepository;
import pap.db.Repository.RentalRepository;
import pap.db.Repository.UserRepository;
import pap.helpers.LoadedPages;
import pap.helpers.Login;
import pap.helpers.PenaltyRecord;

import java.net.URL;
import java.util.ResourceBundle;

public class BrowsePenaltiesController implements UpdatableController, Initializable {
    /**
     * A controller class for browse-penalties page which allows a user to see the list of his penalties
     * and "pay" for them.
     */
    @FXML
    private TableView<PenaltyRecord> penaltyCatalog;
    @FXML
    private TableColumn<PenaltyRecord, Boolean> paid;
    @FXML
    private TableColumn<PenaltyRecord, String> amount;
    @FXML
    private TableColumn<PenaltyRecord, String> datePaid;
    @FXML
    private TableColumn<PenaltyRecord, String> dateAdded;
    @FXML
    private TableColumn<PenaltyRecord, String> bookTitle;
    @FXML
    private TableColumn<PenaltyRecord, String> bookAuthor;
    @FXML
    private TableColumn<PenaltyRecord, String> cause;

    /**
     * A method that lets the user "pay" for the damage penalty.
     */
    private void damagePenalty(int penaltyId) {
        // Check if user wants to pay for the penalty
        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Do you wish to pay for this penalty?",
                ButtonType.YES,
                ButtonType.NO
        );
        var result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            // Update penalty status
            RentalRepository rentalRepo = new RentalRepository();
            Penalty penalty = rentalRepo.getPenaltyById(penaltyId);
            penalty.setDatePaid(new java.sql.Date(System.currentTimeMillis()));
            rentalRepo.updatePenalty(penalty);

            // Update book status
            BookRepository bookRepo = new BookRepository();
            Book book = bookRepo.getById(penaltyCatalog.getSelectionModel().getSelectedItem().getBookId());
            book.setStatus(Book.BookStatus.Available);
            bookRepo.update(book);

            // Update user status
            int userId = penaltyCatalog.getSelectionModel().getSelectedItem().getUserId();
            if (rentalRepo.getUnpaidUserPenalties(userId).isEmpty()) {
                UserRepository userRepo = new UserRepository();
                User user = userRepo.getById(userId);
                user.setHasUnpaidPenalty(false);
                userRepo.update(user);
            }
            update();
        }
    }

    /**
     * A method that lets the user "pay" for the rental overextending penalty.
     */
    @FXML
    public void getItem(MouseEvent event) {
        if (penaltyCatalog.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        int index = penaltyCatalog.getSelectionModel().getSelectedIndex();
        if(index <= -1){
            return;
        }

        // check if penalty is paid
        if (penaltyCatalog.getSelectionModel().getSelectedItem().getPaid()) return;

        // check if penalty is not because of rental overextension
        int penaltyId = penaltyCatalog.getSelectionModel().getSelectedItem().getPenaltyId();
        String cause = penaltyCatalog.getSelectionModel().getSelectedItem().getCause();
        if(!cause.equals(Penalty.PenaltyCause.Late)) {
            damagePenalty(penaltyId);
            return;
        }

        // Check if book has been returned
        BookRental curr = new RentalRepository().getCurrentBookRental(penaltyCatalog.getSelectionModel().getSelectedItem().getBookId());
        if (curr == null || curr.getUserId() != penaltyCatalog.getSelectionModel().getSelectedItem().getUserId()) {
            damagePenalty(penaltyId);
            return;
        }

        // Ask user if he wants to return the book
        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Do you wish to return this book now?",
                ButtonType.YES,
                ButtonType.NO
        );
        var result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            // if user does want to return the book, switch to book-view page
            Book book = new BookRepository().getById(penaltyCatalog.getSelectionModel().getSelectedItem().getBookId());
            BookViewController.setBook(book);
            GlobalController.switchVisibleContent(LoadedPages.bookView);
        }
    }
    @Override
    public void update() {
        penaltyCatalog.getSelectionModel().clearSelection();

        ObservableList<PenaltyRecord> records = FXCollections.observableArrayList(PenaltyRecord.getUserPenalties(Login.getUserLoggedIn().orElse(0)));

        paid.setCellValueFactory(new PropertyValueFactory<>("paid"));
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        datePaid.setCellValueFactory(new PropertyValueFactory<>("datePaid"));
        dateAdded.setCellValueFactory(new PropertyValueFactory<>("dateAdded"));
        bookTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        bookAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        cause.setCellValueFactory(new PropertyValueFactory<>("cause"));

        penaltyCatalog.setItems(records);

        paid.setSortType(TableColumn.SortType.ASCENDING);
        penaltyCatalog.getSortOrder().add(paid);
        dateAdded.setSortType(TableColumn.SortType.ASCENDING);
        penaltyCatalog.getSortOrder().add(dateAdded);
        penaltyCatalog.sort();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        penaltyCatalog.widthProperty().addListener(o -> {
            penaltyCatalog.getColumns().forEach(column -> {
                    column.setMinWidth(penaltyCatalog.getWidth() / penaltyCatalog.getColumns().size());
                });
            });

            update();
    }
}
