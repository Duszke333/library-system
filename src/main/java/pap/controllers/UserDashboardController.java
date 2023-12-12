package pap.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import pap.db.Repository.UserRepository;
import pap.helpers.LoadedPages;
import pap.helpers.Login;

import java.util.List;

public class UserDashboardController implements UpdatableController {
    @FXML
    private ListView<Button> userActions;
    @FXML
    private Text loginInfo;
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
        
        userActions.getItems().setAll(List.of(
                manageItem,
                deactivateAccountItem,
                signOutItem
        ));
    }

    @Override
    public void update() {
        if (loginInfo == null) {
            return;
        }
        
        var user = new UserRepository().getById(Login.getUserLoggedIn().get());
        loginInfo.setFont(Font.font(40));
        loginInfo = new Text(String.format(
                """
                Currently logged in as (ID: %d) %s %s
                Account Info:\s

                Active: %s
                Created in %s
                """,
                user.getAddressId(), user.getFirstName(), user.getLastName(),
                user.isActive(),
                user.getDateCreated()
        ));
    }
}
