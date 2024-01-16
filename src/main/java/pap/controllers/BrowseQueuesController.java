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
import pap.helpers.QueueRecord;

import java.net.URL;
import java.util.ResourceBundle;

public class BrowseQueuesController implements UpdatableController, Initializable {
    /**
     * A controller class for browse-queues page which allows a user to view all the books he is in queue for.
     */
    @FXML
    private TableView<QueueRecord> queueCatalog;
    @FXML
    private TableColumn<QueueRecord, Boolean> ready;
    @FXML
    private TableColumn<QueueRecord, String> pickupDate;
    @FXML
    private TableColumn<QueueRecord, String> bookTitle;
    @FXML
    private TableColumn<QueueRecord, String> bookAuthor;
    @FXML
    private TableColumn<QueueRecord, String> bookLanguage;

    /**
     * A method that switches to book-view page which shows details about the book the user is in queue for.
     */
    @FXML
    public void getItem(MouseEvent event) {
        if (queueCatalog.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        int index = queueCatalog.getSelectionModel().getSelectedIndex();
        if(index <= -1){
            return;
        }
        int chosenBookID = queueCatalog.getSelectionModel().getSelectedItem().getBookId();
        Book chosenBook = new BookRepository().getById(chosenBookID);

        BookViewController.setBook(chosenBook);
        GlobalController.switchVisibleContent(LoadedPages.bookView);
    }
    @Override
    public void update() {
        queueCatalog.getSelectionModel().clearSelection();

        ObservableList<QueueRecord> records = FXCollections.observableArrayList(QueueRecord.getUserQueues(Login.getUserLoggedIn().orElse(1)));

        ready.setCellValueFactory(new PropertyValueFactory<>("ready"));
        pickupDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        bookTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        bookAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        bookLanguage.setCellValueFactory(new PropertyValueFactory<>("language"));

        queueCatalog.setItems(records);

        pickupDate.setSortType(TableColumn.SortType.ASCENDING);
        queueCatalog.getSortOrder().add(pickupDate);
        queueCatalog.sort();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        queueCatalog.widthProperty().addListener(o -> {
            queueCatalog.getColumns().forEach(column -> {
                    column.setMinWidth(queueCatalog.getWidth() / queueCatalog.getColumns().size());
                });
            });

            update();
    }
}
