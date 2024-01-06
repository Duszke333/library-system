package pap.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import pap.db.Entities.Employee;
import pap.db.Repository.EmployeeRepository;
import pap.db.Repository.ReportRepository;
import pap.helpers.LoadedPages;
import pap.helpers.Login;

import java.util.List;
import java.util.Optional;

public class EmployeeDashboardController implements UpdatableController {
    @FXML
    private ListView<Button> employeeActions;
    @FXML
    private Text loginInfo;
    @FXML
    private VBox contentPane;

    @FXML
    private void initialize() {
        var signOutItem = new Button("Sign Out");
        signOutItem.setOnAction(e -> {
            Login.setEmployeeLoggedIn(Optional.empty());
            GlobalController.switchVisibleContent(LoadedPages.loginScreenController, LoadedPages.loginScreen);
        });

        var deactivateAccountItem = new Button("Deactivate account");
        deactivateAccountItem.setOnAction(e -> {
            Alert alert = new Alert(
                    Alert.AlertType.CONFIRMATION,
                    "Are you positive?",
                    ButtonType.YES,
                    ButtonType.NO,
                    ButtonType.CANCEL
            );
            var result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.YES) {
//                Platform.exit(); // TODO Set to inactive and logout
                EmployeeRepository repo = new EmployeeRepository();
                Employee emp = repo.getById(Login.getEmployeeLoggedIn().get());
                emp.setActive(false);
                repo.update(emp);
                Login.setEmployeeLoggedIn(Optional.empty());
                GlobalController.switchVisibleContent(LoadedPages.loginScreenController, LoadedPages.loginScreen);
            }
        });

        var changePassItem = new Button("Change password");
        changePassItem.setOnAction(e -> GlobalController.switchVisibleContent(LoadedPages.employeePasswordChangeController, LoadedPages.employeePasswordChange));

        var manageCatalogueItem = new Button("Manage book catalogue");
        manageCatalogueItem.setOnAction(e -> GlobalController.switchVisibleContent(LoadedPages.manageCatalogController, LoadedPages.manageCatalog));

        var bookCreatorItem = new Button("Add new books");
        bookCreatorItem.setOnAction(e -> GlobalController.switchVisibleContent(LoadedPages.bookCreatorController, LoadedPages.bookCreator));

        var createEmployeeAccountsItem = new Button("Create new employee accounts");
        createEmployeeAccountsItem.setOnAction(e -> GlobalController.switchVisibleContent(LoadedPages.employeeAccountCreateController, LoadedPages.employeeAccountCreate));

        var manageParametersItem = new Button("Manage Parameters");
        manageParametersItem.setOnAction(e -> GlobalController.switchVisibleContent(LoadedPages.employeeManageParametersController, LoadedPages.employeeManageParameters));

        var manageIssuesItem = new Button("Issue Management" + " ( " + new ReportRepository().getAll().size() + " )");
        int test = new ReportRepository().getAll().size();
        if (test != 0){
            manageIssuesItem.setTextFill(Paint.valueOf("red"));
        }
        manageIssuesItem.setOnAction(e -> GlobalController.switchVisibleContent(LoadedPages.manageIssueController, LoadedPages.manageIssue));

        employeeActions.getItems().setAll(List.of(
                manageCatalogueItem,
                manageParametersItem,
                bookCreatorItem,
                manageIssuesItem,
                changePassItem,
                createEmployeeAccountsItem,
                deactivateAccountItem,
                signOutItem

        ));
    }

    @Override
    public void update() {
        var empl = new EmployeeRepository().getById(Login.getEmployeeLoggedIn().get());
        loginInfo.setWrappingWidth(contentPane.getWidth());
        loginInfo.setFont(Font.font(20));
        loginInfo.setText(String.format(
                """
                        Currently logged in as (ID: %d) Username: %s \n Role: %s
                        Account Info:\s

                        Active: %s
                        Created in %s
                        """,
                empl.getEmployeeId(), empl.getUsername(), empl.getRole(),
                empl.isActive(),
                empl.getDateCreated()
        ));
        initialize();

    }
}
