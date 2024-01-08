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
import pap.helpers.PenaltyRecord;
import pap.helpers.QueueRecord;

import java.net.URL;
import java.util.ResourceBundle;

public class BrowsePenaltiesController implements UpdatableController, Initializable {
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

    @FXML
    public void getItem(MouseEvent event) {
        if (penaltyCatalog.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        int index = penaltyCatalog.getSelectionModel().getSelectedIndex();
        if(index <= -1){
            return;
        }
        int chosenBookID = penaltyCatalog.getSelectionModel().getSelectedItem().getBookId();
        Book chosenBook = new BookRepository().getById(chosenBookID);

        BookViewController.setBook(chosenBook);
        GlobalController.switchVisibleContent(LoadedPages.bookView);
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
