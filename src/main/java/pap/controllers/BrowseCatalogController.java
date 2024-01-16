package pap.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import pap.db.Entities.Book;
import pap.db.Repository.BookRepository;
import pap.helpers.CatalogRecord;
import pap.helpers.LoadedPages;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class BrowseCatalogController implements UpdatableController, Initializable {
    /**
     * A controller class for browse-catalog page which allows viewing every book in the system.
     */
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
    private TextField searchBar;
    @FXML
    private ComboBox<String> sortBox;
    @FXML
    private Button clearFilters;

    /**
     * A method that switches to book-view page which shows details about the book.
     */
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

    /**
     * A method that clears the filters and returns to the default view.
     */
    public void clearFiltersClicked(MouseEvent event) {
        sortBox.getSelectionModel().clearSelection();
        update();
    }


    @Override
    public void update() {
        catalog.getSelectionModel().clearSelection();
        clearFilters.setVisible(false);

        ObservableList<CatalogRecord> list = FXCollections.observableArrayList(CatalogRecord.getAll());

        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        author.setCellValueFactory(new PropertyValueFactory<>("author"));
        averageGrade.setCellValueFactory(new PropertyValueFactory<>("averageGrade"));
        genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        language.setCellValueFactory(new PropertyValueFactory<>("language"));
        catalog.setItems(list);

        title.setSortType(TableColumn.SortType.ASCENDING);
        catalog.getSortOrder().add(title);
        catalog.sort();

        applySearchFilter(list);
    }

    /**
     * A method that applies a search filter to the catalog.
     */
    private void applySearchFilter(ObservableList<CatalogRecord> list) {
        FilteredList<CatalogRecord> filteredList = new FilteredList<>(list, b -> true);
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(catalogRecord -> {
                if (newValue == null || newValue.trim().isEmpty()) {
                    return true;
                }
                String searchKeyword = newValue.toLowerCase().trim();
                String title = catalogRecord.getTitle().toLowerCase();
                String author = catalogRecord.getAuthor().toLowerCase();
                return title.contains(searchKeyword) || author.contains(searchKeyword);
            });
        });

        SortedList<CatalogRecord> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(catalog.comparatorProperty());
        catalog.setItems(sortedList);
    }

    /**
     * A method that sets up the sort box.
     */
    private void setupSortBox() {
        ObservableList<String> sortOptions = FXCollections.observableArrayList(
                "Last Week", "Last Month", "Last Year", "All Time"
        );
        sortBox.setItems(sortOptions);
        sortBox.setPromptText("Sort by Popularity");
        sortBox.setOnAction(event -> {
            String selectedTimeFrame = sortBox.getSelectionModel().getSelectedItem();
            if (selectedTimeFrame != null) {
                clearFilters.setVisible(true);
                clearFilters.setText(selectedTimeFrame + " X ");
                switch (selectedTimeFrame) {
                    case "Last Week":
                        sortCatalogByPopularity("week");
                        break;
                    case "Last Month":
                        sortCatalogByPopularity("month");
                        break;
                    case "Last Year":
                        sortCatalogByPopularity("year");
                        break;
                    case "All Time":
                        sortCatalogByPopularity("all_time");
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * A method that sorts the catalog by popularity in a given time period.
     */
    private void sortCatalogByPopularity(String periodType) {
        List<Book> bookList = new BookRepository().getMostPopular(periodType);
        ObservableList<CatalogRecord> catalogRecordObservableList = convertToCatalogRecords(bookList);

        catalog.setItems(catalogRecordObservableList);
        applySearchFilter(catalogRecordObservableList);
    }

    /**
     * A method that converts a list of books to a list of catalog records.
     */
    private ObservableList<CatalogRecord> convertToCatalogRecords(List<Book> bookList) {
        return bookList.stream()
                .map(this::createCatalogRecordFromBook)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }

    private CatalogRecord createCatalogRecordFromBook(Book book) {
        return new CatalogRecord(book);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        catalog.widthProperty().addListener(o -> {
            catalog.getColumns().forEach(column -> {
                column.setMinWidth(catalog.getWidth() / catalog.getColumns().size());
            });
        });
        setupSortBox();
        update();
    }
}
