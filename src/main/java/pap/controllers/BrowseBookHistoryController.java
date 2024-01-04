package pap.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Setter;
import pap.helpers.LoadedPages;
import pap.helpers.RentalRecord;

import java.net.URL;
import java.util.ResourceBundle;

public class BrowseBookHistoryController implements UpdatableController, Initializable {

    @Setter
    static private int bookId=0;
    @FXML
    private TableView<RentalRecord> rentalCatalog;
    @FXML
    private TableColumn<RentalRecord, String> dateReturned;
    @FXML
    private TableColumn<RentalRecord, String> dateToReturn;
    @FXML
    private TableColumn<RentalRecord, String> dateRented;
    @FXML
    private TableColumn<RentalRecord, String> userId;
    @FXML
    private TableColumn<RentalRecord, String> userFirstName;
    @FXML
    private TableColumn<RentalRecord, String> userLastName;

    @FXML
    protected void goBack() {
        GlobalController.switchVisibleContent(LoadedPages.bookManagerController, LoadedPages.bookManager);
    }

    @Override
    public void update() {
        rentalCatalog.getSelectionModel().clearSelection();

        ObservableList<RentalRecord> records = FXCollections.observableArrayList(RentalRecord.getBookRentalHistory(bookId));

        dateReturned.setCellValueFactory(new PropertyValueFactory<RentalRecord, String>("dateReturned"));
        dateToReturn.setCellValueFactory(new PropertyValueFactory<RentalRecord, String>("dateToReturn"));
        userId.setCellValueFactory(new PropertyValueFactory<RentalRecord, String>("userId"));
        userFirstName.setCellValueFactory(new PropertyValueFactory<RentalRecord, String>("userFirstName"));
        userLastName.setCellValueFactory(new PropertyValueFactory<RentalRecord, String>("userLastName"));
        dateRented.setCellValueFactory(new PropertyValueFactory<RentalRecord, String>("dateRented"));

        rentalCatalog.setItems(records);

        dateRented.setSortType(TableColumn.SortType.DESCENDING);
        rentalCatalog.getSortOrder().add(dateRented);
        rentalCatalog.sort();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            rentalCatalog.widthProperty().addListener(o -> {
                rentalCatalog.getColumns().forEach(column -> {
                    column.setMinWidth(rentalCatalog.getWidth() / rentalCatalog.getColumns().size());
                });
            });

            update();
    }
}
