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
import pap.helpers.CatalogRecord;
import pap.helpers.LoadedPages;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class BrowseCatalogController implements UpdatableController, Initializable {
    @FXML
    private TableColumn<CatalogRecord, String> author;
    @FXML
    private TableView<CatalogRecord> catalog;
    @FXML
    private TableColumn<CatalogRecord, String> genre;
    @FXML
    private TableColumn<CatalogRecord, String> language;
    @FXML
    private TableColumn<CatalogRecord, Double> averageGrade;
    @FXML
    private TableColumn<CatalogRecord, String> title;

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
        Book chosenBook = new BookRepository().getById(chosenBookID);

        BookViewController.setBook(chosenBook);
        GlobalController.switchVisibleContent(LoadedPages.bookView);
    }


    @Override
    public void update() {
        catalog.getSelectionModel().clearSelection();

        ObservableList<CatalogRecord> list = FXCollections.observableArrayList(CatalogRecord.getAll());

        title.setCellValueFactory(new PropertyValueFactory<CatalogRecord, String>("title"));
        author.setCellValueFactory(new PropertyValueFactory<CatalogRecord, String>("author"));
        averageGrade.setCellValueFactory(new PropertyValueFactory<CatalogRecord, Double>("averageGrade"));
        genre.setCellValueFactory(new PropertyValueFactory<CatalogRecord, String>("genre"));
        language.setCellValueFactory(new PropertyValueFactory<CatalogRecord, String>("language"));
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
