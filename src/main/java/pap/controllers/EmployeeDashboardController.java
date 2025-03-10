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
    /**
     * A controller class for employee-dashboard page which allows an employee to access all the actions he can perform.
     */
    @FXML
    private ListView<Button> employeeActions;
    @FXML
    private Text loginInfo;
    @FXML
    private VBox contentPane;

    /**
     * A method that initializes the page by creating all the necessery buttons.
     */
    @FXML
    private void initialize() {
        var signOutItem = new Button("Sign Out");
        signOutItem.setOnAction(e -> {
            Login.setEmployeeLoggedIn(Optional.empty());
            GlobalController.switchVisibleContent(LoadedPages.loginScreen);
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
                EmployeeRepository repo = new EmployeeRepository();
                Employee emp = repo.getById(Login.getEmployeeLoggedIn().get());
                emp.setActive(false);
                repo.update(emp);
                
                // Sign out
                Login.setEmployeeLoggedIn(Optional.empty());
                GlobalController.switchVisibleContent(LoadedPages.loginScreen);
            }
        });

        var changePassItem = new Button("Change password");
        changePassItem.setOnAction(e -> GlobalController.switchVisibleContent(LoadedPages.employeePasswordChange));

        var manageCatalogueItem = new Button("Manage book catalogue");
        manageCatalogueItem.setOnAction(e -> GlobalController.switchVisibleContent(LoadedPages.manageCatalog));

        var bookCreatorItem = new Button("Add new books");
        bookCreatorItem.setOnAction(e -> GlobalController.switchVisibleContent(LoadedPages.bookCreator));

        var createEmployeeAccountsItem = new Button("Create new employee accounts");
        createEmployeeAccountsItem.setOnAction(e -> GlobalController.switchVisibleContent(LoadedPages.employeeAccountCreate));

        var manageParametersItem = new Button("Manage Parameters");
        manageParametersItem.setOnAction(e -> GlobalController.switchVisibleContent(LoadedPages.employeeManageParameters));

        int size = new ReportRepository().getUnresolved().size();
        var manageIssuesItem = new Button();
        if (size != 0) {
            manageIssuesItem.setTextFill(Paint.valueOf("red"));
            manageIssuesItem.setText("Manage Issues" + " ( " + size + " )");
        } else {
            manageIssuesItem.setText("Manage Issues");
            manageIssuesItem.setTextFill(Paint.valueOf("white"));
        }

        manageIssuesItem.setOnAction(e -> GlobalController.switchVisibleContent(LoadedPages.employeeIssueManage));
        
        var manageBranches = new Button("Manage Branches");
        manageBranches.setOnAction(e -> GlobalController.switchVisibleContent(LoadedPages.employeeManageBranches));

        employeeActions.getItems().setAll(List.of(
                manageCatalogueItem,
                manageBranches,
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
        if (Login.getEmployeeLoggedIn().isEmpty()) {
            return;
        }
        
        var employee = new EmployeeRepository().getById(Login.getEmployeeLoggedIn().get());
        loginInfo.setWrappingWidth(contentPane.getWidth());
        loginInfo.setFont(Font.font(20));
        loginInfo.setText(String.format(
                """
                        Currently logged in as (ID: %d) Username: %s \n Role: %s
                        Account Info:\s

                        Active: %s
                        Created in %s
                        """,
                employee.getEmployeeId(), employee.getUsername(), employee.getRole(),
                employee.isActive(),
                employee.getDateCreated()
        ));
        initialize();
    }
}
