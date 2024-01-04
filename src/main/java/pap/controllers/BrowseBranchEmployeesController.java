package pap.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Setter;
import pap.helpers.BranchEmployeeRecord;

import java.net.URL;
import java.util.ResourceBundle;

public class BrowseBranchEmployeesController implements UpdatableController, Initializable {
    @Setter
    private static int branchId=0;
    @FXML
    private TableView<BranchEmployeeRecord> branchEmployeeCatalog;
    @FXML
    private TableColumn<BranchEmployeeRecord, String> firstName;
    @FXML
    private TableColumn<BranchEmployeeRecord, String> lastName;
    @FXML
    private TableColumn<BranchEmployeeRecord, String> role;
    @Override
    public void update() {
        branchEmployeeCatalog.getSelectionModel().clearSelection();

        ObservableList<BranchEmployeeRecord> records = FXCollections.observableArrayList(BranchEmployeeRecord.getBranchEmployees(branchId));

        firstName.setCellValueFactory(new PropertyValueFactory<BranchEmployeeRecord, String>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<BranchEmployeeRecord, String>("lastName"));
        role.setCellValueFactory(new PropertyValueFactory<BranchEmployeeRecord, String>("role"));

        branchEmployeeCatalog.setItems(records);

        role.setSortType(TableColumn.SortType.ASCENDING);
        branchEmployeeCatalog.getSortOrder().add(role);
        branchEmployeeCatalog.sort();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        branchEmployeeCatalog.widthProperty().addListener(o -> {
            branchEmployeeCatalog.getColumns().forEach(column -> {
                    column.setMinWidth(branchEmployeeCatalog.getWidth() / branchEmployeeCatalog.getColumns().size());
                });
            });

            update();
    }
}
