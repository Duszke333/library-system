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
import pap.helpers.WishRecord;

import java.net.URL;
import java.util.ResourceBundle;

public class BrowseWishListController implements UpdatableController, Initializable {
    /**
     * A controller class for browse-wish-list page which allows a user to view all the books he added to his wishlist.
     */
    @FXML
    private TableView<WishRecord> wishCatalog;
    @FXML
    private TableColumn<WishRecord, String> dateAdded;
    @FXML
    private TableColumn<WishRecord, String> bookTitle;
    @FXML
    private TableColumn<WishRecord, String> bookAuthor;

    /**
     * A method that switches to book-view page which shows details about the book the user has added to wish list.
     */
    @FXML
    public void getItem(MouseEvent event) {
        if (wishCatalog.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        int index = wishCatalog.getSelectionModel().getSelectedIndex();
        if(index <= -1){
            return;
        }
        int chosenBookID = wishCatalog.getSelectionModel().getSelectedItem().getBookId();
        Book chosenBook = new BookRepository().getById(chosenBookID);

        BookViewController.setBook(chosenBook);
        GlobalController.switchVisibleContent(LoadedPages.bookView);
    }
    @Override
    public void update() {
        wishCatalog.getSelectionModel().clearSelection();

        ObservableList<WishRecord> records = FXCollections.observableArrayList(WishRecord.getWishList(Login.getUserLoggedIn().orElse(1)));

        bookTitle.setCellValueFactory(new PropertyValueFactory<WishRecord, String>("title"));
        bookAuthor.setCellValueFactory(new PropertyValueFactory<WishRecord, String>("author"));
        dateAdded.setCellValueFactory(new PropertyValueFactory<WishRecord, String>("dateAdded"));

        wishCatalog.setItems(records);

        dateAdded.setSortType(TableColumn.SortType.ASCENDING);
        wishCatalog.getSortOrder().add(dateAdded);
        wishCatalog.sort();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        wishCatalog.widthProperty().addListener(o -> {
            wishCatalog.getColumns().forEach(column -> {
                column.setMinWidth(wishCatalog.getWidth() / wishCatalog.getColumns().size());
            });
        });

        update();
    }
}
