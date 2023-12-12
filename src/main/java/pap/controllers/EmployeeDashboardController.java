package pap.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import pap.db.Repository.EmployeeRepository;
import pap.helpers.LoadedPages;
import pap.helpers.Login;

import java.util.List;

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
            GlobalController.switchVisibleContent(LoadedPages.loginScreenController, LoadedPages.loginScreen);
            Login.resetToken();
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
                Platform.exit();
            }
        });

        var manageItem = new Button("Manage account settings");
        manageItem.setOnAction(e -> GlobalController.switchVisibleContent(LoadedPages.userAccountManageController, LoadedPages.userAccountManage));

        employeeActions.getItems().setAll(List.of(
                manageItem,
                deactivateAccountItem,
                signOutItem
        ));
    }
    
    @Override
    public void update() {
        var empl = new EmployeeRepository().getById(Login.getUserLoggedIn().get());
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
    }
}
