package pap.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import pap.db.Repository.UserRepository;
import pap.helpers.LoadedPages;
import pap.helpers.Login;

import java.util.List;
import java.util.Optional;

public class UserDashboardController implements UpdatableController {
    @FXML
    private ListView<Button> userActions;
    @FXML
    private Text loginInfo;
    @FXML
    private VBox contentPane;
    
    @FXML
    private void initialize() {
        var signOutItem = new Button("Sign Out");
        signOutItem.setOnAction(e -> {
            Login.setUserLoggedIn(Optional.empty());
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
                Platform.exit();
            }
        });
        
        var manageItem = new Button("Manage account settings");
        manageItem.setOnAction(e -> GlobalController.switchVisibleContent(LoadedPages.userAccountManageController, LoadedPages.userAccountManage));
        
        var browseRentalItem = new Button("Browse rented books");
        browseRentalItem.setOnAction(e -> GlobalController.switchVisibleContent(LoadedPages.browseRentalController, LoadedPages.browseRental));

        var browseHistoryItem = new Button("Browse renting history"); 
        browseHistoryItem.setOnAction(e -> GlobalController.switchVisibleContent(LoadedPages.browseHistoryController, LoadedPages.browseHistory));

        var browseWishListItem = new Button("Browse wish list");
        browseWishListItem.setOnAction(e -> GlobalController.switchVisibleContent(LoadedPages.browseWishListController, LoadedPages.browseWishList));
        
        userActions.getItems().setAll(List.of(
                manageItem,
                browseRentalItem,
                browseHistoryItem,
                browseWishListItem,
                deactivateAccountItem,
                signOutItem
        ));
    }

    @Override
    public void update() {
        var user = new UserRepository().getById(Login.getUserLoggedIn().get());
        loginInfo.setWrappingWidth(contentPane.getWidth());
        loginInfo.setFont(Font.font(20));
        loginInfo.setText(String.format(
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
