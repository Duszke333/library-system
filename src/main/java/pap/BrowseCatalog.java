package pap;

import db.Entities.Book;
import db.Repository.BookRepository;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

import java.util.List;

public class BrowseCatalog {
    private List<Book> books;
    @FXML
    private TableView bookCatalog;

    public void initialize() {
        // get all books from database
        books = new db.DAO.BookDAO().getAll();
        // set books to table
        bookCatalog.setItems((ObservableList) books);
    }
}
