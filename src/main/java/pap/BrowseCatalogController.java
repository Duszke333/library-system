package pap;


import db.Entities.Book;
import db.Repository.BookRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.*;

public class BrowseCatalogController implements Initializable {

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Book[] booksArray = new BookRepository().getAllBookType();
        ObservableList<Book> list = FXCollections.observableArrayList();
        list.addAll(booksArray);

        title.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
        author.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
        description.setCellValueFactory(new PropertyValueFactory<Book, String>("description"));
        genre.setCellValueFactory(new PropertyValueFactory<Book, String>("genre"));
        language.setCellValueFactory(new PropertyValueFactory<Book, String>("language"));
        page_count.setCellValueFactory(new PropertyValueFactory<Book, Integer>("pageCount"));
        publication_year.setCellValueFactory(new PropertyValueFactory<Book, Date>("publicationYear"));
        publisher.setCellValueFactory(new PropertyValueFactory<Book, String>("publisher"));
        catalog.widthProperty().addListener(o -> {
            catalog.getColumns().forEach(column -> {
                column.setMinWidth(catalog.getWidth() / catalog.getColumns().size());
            });
        });
        catalog.setItems(list);
    }
}
