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
import pap.helpers.BookViewLoader;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class BrowseCatalogController implements UpdatableController, Initializable {
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
        if (catalog.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        int index = catalog.getSelectionModel().getSelectedIndex();
        if(index <= -1){
            return;
        }
        int chosenBookID = catalog.getSelectionModel().getSelectedItem().getBookId();
        Book choosenBook = new BookRepository().getById(chosenBookID);

        BookViewLoader.load(choosenBook);
    }


    @Override
    public void update() {
        catalog.getSelectionModel().clearSelection();

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

        title.setSortType(TableColumn.SortType.ASCENDING);
        catalog.getSortOrder().add(title);
        catalog.sort();
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
