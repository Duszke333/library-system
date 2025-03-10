package pap.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import pap.db.Repository.BranchRepository;
import pap.helpers.BranchRecord;
import pap.helpers.LoadedPages;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeManageBranchesController implements UpdatableController, Initializable {
    /**
     * A controller class for employee-manage-branches page that allows an employee
     * to view all the library branches and edit them if needed.
     */
    @FXML
    private TableView<BranchRecord> branchCatalog;
    @FXML
    private TableColumn<BranchRecord, String> name;
    @FXML
    private TableColumn<BranchRecord, String> address;

    /**
     * A method that switches to branch-manage page which allows to edit information about the branch the employee has chosen.
     */
    @FXML
    public void getItem(MouseEvent event) {
        if (branchCatalog.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        int index = branchCatalog.getSelectionModel().getSelectedIndex();
        if(index <= -1){
            return;
        }
        int chosenBranchID = branchCatalog.getSelectionModel().getSelectedItem().getBranchId();

        BranchManagerController.setBranch(new BranchRepository().getById(chosenBranchID));
        BranchManagerController.setCreationMode(false);
        GlobalController.switchVisibleContent(LoadedPages.branchManage);
    }

    /**
     * A method that switches to branch-manage page which allows to create a new branch.
     */
    @FXML
    protected void addPressed() {
        BranchManagerController.setCreationMode(true);
        GlobalController.switchVisibleContent(LoadedPages.branchManage);
    }

    @Override
    public void update() {
        branchCatalog.getSelectionModel().clearSelection();

        ObservableList<BranchRecord> records = FXCollections.observableArrayList(BranchRecord.getAll());

        name.setCellValueFactory(new PropertyValueFactory<BranchRecord, String>("name"));
        address.setCellValueFactory(new PropertyValueFactory<BranchRecord, String>("address"));

        branchCatalog.setItems(records);

        name.setSortType(TableColumn.SortType.ASCENDING);
        branchCatalog.getSortOrder().add(name);
        branchCatalog.sort();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            branchCatalog.widthProperty().addListener(o -> {
                branchCatalog.getColumns().forEach(column -> {
                    column.setMinWidth(branchCatalog.getWidth() / branchCatalog.getColumns().size());
                });
            });

            update();
    }
}
