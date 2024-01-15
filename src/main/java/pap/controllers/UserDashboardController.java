package pap.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import pap.db.Entities.BookRental;
import pap.db.Repository.RentalRepository;
import pap.db.Repository.UserRepository;
import pap.helpers.LoadedPages;
import pap.helpers.Login;
import pap.helpers.PenaltyManager;

import java.util.List;
import java.util.Optional;

public class UserDashboardController implements UpdatableController {
    /**
     * A controller class for user-dashboard page.
     */
    @FXML
    private ListView<Button> userActions;
    @FXML
    private Text loginInfo;
    @FXML
    private VBox contentPane;

    @FXML
    private void checkDeactivation() {
        /*
            A method that runs the account deactivation process.
         */
        int uid = Login.getUserLoggedIn().get();
        var user = new UserRepository().getById(uid);

        // check if user has unpaid penalties
        if (user.isHasUnpaidPenalty()) {
            Alert alert = new Alert(
                    Alert.AlertType.WARNING,
                    "You have unpaid penalties. You cannot deactivate your account until you pay them.",
                    ButtonType.OK
            );
            alert.showAndWait();
            return;
        }

        // check if user has active rentals
        List<BookRental> currentRentals = new RentalRepository().getUserCurrentRentals(uid);
        if (!currentRentals.isEmpty()) {
            Alert alert = new Alert(
                    Alert.AlertType.WARNING,
                    "You have active rentals. You cannot deactivate your account until you return all books.",
                    ButtonType.OK
            );
            alert.showAndWait();
            return;
        }

        // ask if the user is certain that they want to deactivate their account
        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Are you positive?",
                ButtonType.YES,
                ButtonType.NO,
                ButtonType.CANCEL
        );
        var result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            // Deactivate user account
            user.setActive(false);
            new UserRepository().update(user);
            // remove user from queues
            PenaltyManager.removeUserFromQueues(uid);
            // sign out
            Login.setUserLoggedIn(Optional.empty());
            GlobalController.switchVisibleContent(LoadedPages.loginScreen);
        }
    }

    @FXML
    private void initialize() {
        /*
            A method that initializes the page by setting up all the needed buttons.
         */
        var signOutItem = new Button("Sign Out");
        signOutItem.setOnAction(e -> {
            Login.setUserLoggedIn(Optional.empty());
            GlobalController.switchVisibleContent(LoadedPages.loginScreen);
        });

        var deactivateAccountItem = new Button("Deactivate account");
        deactivateAccountItem.setOnAction(e -> checkDeactivation());
        
        var manageItem = new Button("Manage account settings");
        manageItem.setOnAction(e -> GlobalController.switchVisibleContent(LoadedPages.userAccountManage));
        
        var browseRentalItem = new Button("Browse rented books");
        browseRentalItem.setOnAction(e -> GlobalController.switchVisibleContent(LoadedPages.browseRental));


        var browsePenaltiesItem = new Button();
        int unpaidCount = new RentalRepository().getUnpaidUserPenalties(Login.getUserLoggedIn().orElse(0)).size();
        if (unpaidCount != 0) {
            browsePenaltiesItem.setText(String.format("Browse penalties (%d)", unpaidCount));
            browsePenaltiesItem.setTextFill(javafx.scene.paint.Paint.valueOf("red"));
        } else {
            browsePenaltiesItem.setText("Browse penalties");
        }
        browsePenaltiesItem.setOnAction(e -> GlobalController.switchVisibleContent(LoadedPages.browsePenalties));

        var browseHistoryItem = new Button("Browse renting history"); 
        browseHistoryItem.setOnAction(e -> GlobalController.switchVisibleContent(LoadedPages.browseRentingHistory));

        var browseWishListItem = new Button("Browse wishlist");
        browseWishListItem.setOnAction(e -> GlobalController.switchVisibleContent(LoadedPages.browseWishList));

        var browseQueuesItem = new Button("Browse your reservations");
        browseQueuesItem.setOnAction(e -> GlobalController.switchVisibleContent(LoadedPages.browseQueues));
        
        userActions.getItems().setAll(List.of(
                manageItem,
                browseRentalItem,
                browsePenaltiesItem,
                browseHistoryItem,
                browseQueuesItem,
                browseWishListItem,
                deactivateAccountItem,
                signOutItem
        ));
    }

    @Override
    public void update() {
        initialize();
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
