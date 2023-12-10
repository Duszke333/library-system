package pap;

import db.Entities.Book;
import db.Repository.BookRepository;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;
import java.util.Objects;

public class BrowseCatalog {
    private List<Book> books;
    @FXML
    private TableView<Book> bookCatalog;

    public void initialize() {
        //TODO
        // get all books from database
        // cos w stylu book = BookRepository.getAll();
        // set books to table
        bookCatalog.widthProperty().addListener(o -> {
            bookCatalog.getColumns().forEach(column -> {
                column.setMinWidth(bookCatalog.getWidth() / bookCatalog.getColumns().size());
            });
        });
    }
}
