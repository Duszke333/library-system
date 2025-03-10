package pap.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import pap.Pap;
import pap.db.Entities.Book;
import pap.db.Repository.BookRepository;
import pap.helpers.LoadedPages;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class ManageCatalogController implements UpdatableController, Initializable {
    /**
     * A controller class for manage-catalog page which allows an employee
     * to select a book and edit information about it or delete it from the system.
     */
    public static Integer chosenBookID = 1;
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

    /**
     * A method that switches to book-manager page which allows to edit information about the book the employee has chosen.
     */
    @FXML
    protected void manageItem(MouseEvent click) throws IOException {
        int index = catalog.getSelectionModel().getSelectedIndex();
        chosenBookID = catalog.getSelectionModel().getSelectedItem().getBookId();
        if(index <= -1){
            return;
        }
        GlobalController.switchVisibleContent(LoadedPages.bookManager);
    }

    @Override
    public void update() {
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
        catalog.widthProperty().addListener(o -> {
            catalog.getColumns().forEach(column -> {
                column.setMinWidth(catalog.getWidth() / catalog.getColumns().size());
            });
        });
        
        update();
    }
}
