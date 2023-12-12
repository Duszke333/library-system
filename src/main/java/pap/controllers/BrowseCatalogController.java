package pap.controllers;



import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pap.Pap;
import pap.controllers.BookViewController;
import pap.db.Entities.Book;
import pap.db.Repository.BookRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class BrowseCatalogController implements Updateable, Initializable {

    @FXML
    private TableColumn<Book, String> author;

    @FXML
    private TableView<Book> catalog;

    @FXML
    private TableColumn<Book, String> description;

    @FXML
    private TableColumn<Book, String> genre;

    @FXML
    private TableColumn<Book, String> language;

    @FXML
    private TableColumn<Book, Integer> page_count;

    @FXML
    private TableColumn<Book, Date> publication_year;

    @FXML
    private TableColumn<Book, String> publisher;

    @FXML
    private TableColumn<Book, String> title;



    @FXML
    public void getItem(MouseEvent event) {
        int index = catalog.getSelectionModel().getSelectedIndex();
        int chosenBookID = catalog.getSelectionModel().getSelectedItem().getBookId();
        if(index <= -1){
            return;
        }
        Book choosenBook = new BookRepository().getById(chosenBookID);

        try {
            FXMLLoader loader = new FXMLLoader(Pap.class.getResource("view/book-view.fxml"));
            Parent root = loader.load();
            Stage stage;
            BookViewController bookViewController = loader.getController();
            bookViewController.setBook(choosenBook);
            bookViewController.displayTitle(choosenBook.getTitle());
            bookViewController.displayAuthor(choosenBook.getAuthor());
            bookViewController.displayGenre(choosenBook.getGenre());
            bookViewController.displayPublicationYear(choosenBook.getPublicationYear());
            bookViewController.displayLanguage(choosenBook.getLanguage());
            bookViewController.displayPageCount(choosenBook.getPageCount());
            bookViewController.displayPublisher(choosenBook.getPublisher());
            bookViewController.displayDescription(choosenBook.getDescription());
            bookViewController.displayAvailability(choosenBook.getStatus());
            bookViewController.displayDateAdded(choosenBook.getDateAdded());
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void update() {
        catalog.widthProperty().addListener(o -> {
            catalog.getColumns().forEach(column -> {
                column.setMinWidth(catalog.getWidth() / catalog.getColumns().size());
            });
        });

        ObservableList<Book> list = FXCollections.observableArrayList(new BookRepository().getAll());

        title.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
        author.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
        description.setCellValueFactory(new PropertyValueFactory<Book, String>("description"));
        genre.setCellValueFactory(new PropertyValueFactory<Book, String>("genre"));
        language.setCellValueFactory(new PropertyValueFactory<Book, String>("language"));
        page_count.setCellValueFactory(new PropertyValueFactory<Book, Integer>("pageCount"));
        publication_year.setCellValueFactory(new PropertyValueFactory<Book, Date>("publicationYear"));
        publisher.setCellValueFactory(new PropertyValueFactory<Book, String>("publisher"));
        catalog.setItems(list);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        update();
    }
}
