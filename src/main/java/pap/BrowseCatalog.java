package pap;

import db.Entities.Book;
import db.Repository.BookRepository;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class BrowseCatalog implements Initializable {
    @FXML
    private TableView<Book> bookCatalog;
    @FXML
    private TableColumn<Book, String> bookTitle;
    @FXML
    private TableColumn<Book, String> bookAuthor;
    @FXML
    private TableColumn<Book, String> bookGenre;
    @FXML
    private TableColumn<Book, String> bookLanguage;
    @FXML
    private TableColumn<Book, String> bookPageCount;
    @FXML
    private TableColumn<Book, String> bookPublicationYear;
    @FXML
    private TableColumn<Book, String> bookPublisher;
    @FXML
    private TableColumn<Book, String> bookDescription;
    @FXML
    private TableColumn<Book, String> bookISBN;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Book> books = FXCollections.observableArrayList(new BookRepository().getAll());
        bookCatalog.widthProperty().addListener(o -> {
            bookCatalog.getColumns().forEach(column -> {
                column.setMinWidth(bookCatalog.getWidth() / bookCatalog.getColumns().size());
            });
        });
        bookCatalog.setItems(books);

        bookTitle.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        bookAuthor.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));
        bookGenre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGenre()));
        bookLanguage.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLanguage()));
        bookPageCount.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPageCount())));
        bookPublicationYear.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPublicationYear())));
        bookPublisher.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPublisher()));
        bookDescription.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
        bookISBN.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIsbn()));
    }
}
