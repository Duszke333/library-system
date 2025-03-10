package pap.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import pap.db.Entities.Book;
import pap.db.Repository.BookRepository;
import pap.helpers.LoadedPages;
import pap.helpers.Login;
import pap.helpers.RentalRecord;

import java.net.URL;
import java.util.ResourceBundle;

public class BrowseRentingHistoryController implements UpdatableController, Initializable {
    /**
     * A controller class for browse-renting-history page which allows a user to view
     * all the books that he rented in the past.
     */
    @FXML
    private TableView<RentalRecord> rentalCatalog;
    @FXML
    private TableColumn<RentalRecord, String> dateReturned;
    @FXML
    private TableColumn<RentalRecord, String> dateRented;
    @FXML
    private TableColumn<RentalRecord, String> bookTitle;
    @FXML
    private TableColumn<RentalRecord, String> bookAuthor;
    @FXML
    private TableColumn<RentalRecord, String> bookLanguage;

    /**
     * A method that switches to book-view page which shows details about the book the user has rented in the past.
     */
    @FXML
    public void getItem(MouseEvent event) {
        if (rentalCatalog.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        int index = rentalCatalog.getSelectionModel().getSelectedIndex();
        if(index <= -1){
            return;
        }
        int chosenBookID = rentalCatalog.getSelectionModel().getSelectedItem().getBookId();
        Book chosenBook = new BookRepository().getById(chosenBookID);

        BookViewController.setBook(chosenBook);
        GlobalController.switchVisibleContent(LoadedPages.bookView);
    }
    @Override
    public void update() {
        rentalCatalog.getSelectionModel().clearSelection();

        ObservableList<RentalRecord> records = FXCollections.observableArrayList(RentalRecord.getRentalHistory(Login.getUserLoggedIn().orElse(1)));

        dateReturned.setCellValueFactory(new PropertyValueFactory<RentalRecord, String>("dateReturned"));
        bookTitle.setCellValueFactory(new PropertyValueFactory<RentalRecord, String>("title"));
        bookAuthor.setCellValueFactory(new PropertyValueFactory<RentalRecord, String>("author"));
        bookLanguage.setCellValueFactory(new PropertyValueFactory<RentalRecord, String>("language"));
        dateRented.setCellValueFactory(new PropertyValueFactory<RentalRecord, String>("dateRented"));

        rentalCatalog.setItems(records);

        dateReturned.setSortType(TableColumn.SortType.DESCENDING);
        rentalCatalog.getSortOrder().add(dateReturned);
        rentalCatalog.sort();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            rentalCatalog.widthProperty().addListener(o -> {
                rentalCatalog.getColumns().forEach(column -> {
                    column.setMinWidth(rentalCatalog.getWidth() / rentalCatalog.getColumns().size());
                });
            });

            update();
    }
}
