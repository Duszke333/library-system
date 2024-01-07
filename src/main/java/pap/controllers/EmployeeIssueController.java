package pap.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import pap.helpers.IssueRecord;
import pap.helpers.LoadedPages;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class EmployeeIssueController implements UpdatableController, Initializable {

    @FXML
    private TableView<IssueRecord> issueCatalog;
    @FXML
    private TableColumn<IssueRecord, Boolean> resolved;
    @FXML
    private TableColumn<IssueRecord, Date> dateReported;
    @FXML
    private TableColumn<IssueRecord, Integer> userId;
    @FXML
    private TableColumn<IssueRecord, String> userFirstName;
    @FXML
    private TableColumn<IssueRecord, String> userLastName;
    @FXML
    private TableColumn<IssueRecord, String> title;
    @FXML
    private TableColumn<IssueRecord, String> author;
    @FXML
    private TableColumn<IssueRecord, String> reportType;
    @FXML
    private TableColumn<IssueRecord, Integer> bookId;



    @Override
    public void update() {
        issueCatalog.getSelectionModel().clearSelection();

        ObservableList<IssueRecord> list = FXCollections.observableArrayList(IssueRecord.getAll());
        resolved.setCellValueFactory(new PropertyValueFactory<IssueRecord, Boolean>("resolved"));
        dateReported.setCellValueFactory(new PropertyValueFactory<IssueRecord, Date>("dateReported"));
        reportType.setCellValueFactory(new PropertyValueFactory<IssueRecord, String>("reportType"));
        userId.setCellValueFactory(new PropertyValueFactory<IssueRecord, Integer>("userId"));
        userFirstName.setCellValueFactory(new PropertyValueFactory<IssueRecord, String>("userFirstName"));
        userLastName.setCellValueFactory(new PropertyValueFactory<IssueRecord, String>("userLastName"));
        title.setCellValueFactory(new PropertyValueFactory<IssueRecord, String >("title"));
        author.setCellValueFactory(new PropertyValueFactory<IssueRecord, String>("author"));
        bookId.setCellValueFactory(new PropertyValueFactory<IssueRecord, Integer>("bookId"));
        issueCatalog.setItems(list);

        resolved.setSortType(TableColumn.SortType.ASCENDING);
        issueCatalog.getSortOrder().add(resolved);
        dateReported.setSortType(TableColumn.SortType.ASCENDING);
        issueCatalog.getSortOrder().add(dateReported);
        issueCatalog.sort();
    }

    @FXML
    public void getItem(MouseEvent event) {
        if (issueCatalog.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        int index = issueCatalog.getSelectionModel().getSelectedIndex();
        if(index <= -1){
            return;
        }
        IssueRecord selectedItem = issueCatalog.getSelectionModel().getSelectedItem();
        EmployeeShowIssueController.setSelectedIssueRecord(selectedItem);
        GlobalController.switchVisibleContent(LoadedPages.employeeShowIssue);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        issueCatalog.widthProperty().addListener(o -> {
            issueCatalog.getColumns().forEach(column -> {
                column.setMinWidth(issueCatalog.getWidth() / issueCatalog.getColumns().size());
            });
        });

        update();
    }

}
