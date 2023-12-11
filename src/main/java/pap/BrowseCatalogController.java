package pap;

import db.DAO.BookDAO;
import db.Entities.Book;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

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

    Integer index;
    Integer choosenBookID;
    @FXML
    public void getItem(MouseEvent event) {
        index = catalog.getSelectionModel().getSelectedIndex();
        choosenBookID = catalog.getSelectionModel().getSelectedItem().getBookId();
        if(index <= -1){
            return;
        }
        Book choosenBook = new BookDAO().read(choosenBookID);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("book-view.fxml"));
            Parent root;
            root = loader.load();
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
            bookViewController.displayAvailability(choosenBook.isAvailable());
            bookViewController.displayDateAdded(choosenBook.getDateAdded());
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    private Book test(){
        BookDAO dao = new BookDAO();
        Book book = new Book();
        
        book.setAuthor("Author");
        book.setIsbn("123");
        book.setGenre("genre");
        book.setLanguage("pl");
        book.setTitle("to jest tytul ksiazki");
        book.setDateAdded(java.sql.Date.valueOf("2023-12-12"));
        book.setDescription("1234");
        book.setPublisher("publisher");
        book.setPageCount(12);
        book.setAvailable(true);
        book.setPublicationYear(2020);
        book.setBookId(2);

        dao.create(book);
        return dao.read(2);
    }

    ObservableList<Book> list = FXCollections.observableArrayList(
//            new Book(1, "ISBN123", "Book Title 1", "Author 1", "Fiction", 2020,
//                    "English", 300, "Publisher A", true,
//                    "Description for Book 1", java.sql.Date.valueOf("2023-01-01"))
            test()
            // docelowo np lista wszystkich ksiazek


    );

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
        //catalog.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Book>() {
//            @Override
//            public void changed(ObservableValue<? extends Book> observableValue, Book book, Book t1) {
//                try {
//                    Parent root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
//                    Stage stage = (Stage) catalog.getScene().getWindow();
//                    Scene scene = new Scene(root);
//                    stage.setScene(scene);
//                    stage.show();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//
//            }
//        });
    }

}
