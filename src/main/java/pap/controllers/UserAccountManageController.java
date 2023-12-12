package pap.controllers;

import pap.db.Entities.Address;
import pap.db.Entities.User;
import pap.db.Repository.AddressRepository;
import pap.db.Repository.UserRepository;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import pap.helpers.LoadedPages;
import pap.helpers.Login;
import pap.helpers.PasswordHasher;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserAccountManageController implements UpdatableController, Initializable {
    private User user;
    private Address address;
    @FXML
    private PasswordField passwordInput;
    @FXML
    private PasswordField newPasswordInput;
    @FXML
    private PasswordField newPasswordConfirmation;
    @FXML
    private TextField countryInput;
    @FXML
    private TextField cityInput;
    @FXML
    private TextField streetInput;
    @FXML
    private TextField postalCodeInput;
    @FXML
    private TextField houseNumberInput;
    @FXML
    private TextField flatNumberInput;
    @FXML
    private Text passUnmatched;
    @FXML
    private Text updateStatus;
    @FXML
    private Text deactivationStatus;
    @FXML
    private Button deactivationButton;
    @FXML
    private Button confirmDeactivation;
    @FXML
    private Button cancelDeactivation;
    
    @FXML
    protected void signOutButtonPressed() {
        Login.setUserLoggedIn(Optional.empty());
        GlobalController.switchVisibleContent(this, LoadedPages.loginPage);
    }

    @FXML
    protected void changePassword() {
        passUnmatched.setFill(javafx.scene.paint.Color.RED);
        passUnmatched.setVisible(false);
        String password = passwordInput.getText();
        String newPassword = newPasswordInput.getText();
        String newPasswordConfirm = newPasswordConfirmation.getText();

        if (password.isEmpty() || newPassword.isEmpty() || newPasswordConfirm.isEmpty()) {
            passUnmatched.setText("All fields must be filled!");
            passUnmatched.setVisible(true);
            return;
        }

        if (!PasswordHasher.hashPassword(password, user.getPasswordSalt()).equals(user.getPasswordHash())) {
            passUnmatched.setText("Wrong password!");
            passUnmatched.setVisible(true);
            return;
        }

        if (!newPassword.equals(newPasswordConfirm)) {
            passUnmatched.setText("New passwords do not match!");
            passUnmatched.setVisible(true);
            return;
        }

        if (password.equals(newPassword)) {
            passUnmatched.setText("New password cannot be the same as old one!");
            passUnmatched.setVisible(true);
            return;
        }

        String salt = PasswordHasher.generateSalt();
        String hashedPassword = PasswordHasher.hashPassword(newPassword, salt);
        user.setPasswordHash(hashedPassword);
        user.setPasswordSalt(salt);

        new UserRepository().update(user);
        passwordInput.clear();
        newPasswordInput.clear();
        newPasswordConfirmation.clear();

        passUnmatched.setText("Password changed successfully!");
        passUnmatched.setFill(javafx.scene.paint.Color.GREEN);
        passUnmatched.setVisible(true);
    }

    @FXML
    protected void updateAddress() {
        updateStatus.setFill(javafx.scene.paint.Color.RED);
        updateStatus.setVisible(false);
        String country = countryInput.getText();
        String city = cityInput.getText();
        String street = streetInput.getText();
        String postalCode = postalCodeInput.getText();
        String houseNumber = houseNumberInput.getText();
        String flatNumber = flatNumberInput.getText();

        if (country.isEmpty() || city.isEmpty() || street.isEmpty() || postalCode.isEmpty() || houseNumber.isEmpty()) {
            updateStatus.setText("All fields must be filled!");
            updateStatus.setVisible(true);
            return;
        }

        address.setCountry(country);
        address.setCity(city);
        address.setStreet(street);
        address.setPostalCode(postalCode);
        address.setHouseNumber(houseNumber);
        address.setFlatNumber(flatNumber);

        new AddressRepository().update(address);

        updateStatus.setText("Address updated successfully!");
        updateStatus.setFill(javafx.scene.paint.Color.GREEN);
        updateStatus.setVisible(true);
    }

    @FXML
    protected void deactivateAccount() {
        deactivationStatus.setFill(javafx.scene.paint.Color.WHITE);
        deactivationStatus.setText("Are you sure? Please confirm.");
        deactivationStatus.setVisible(true);
        deactivationButton.setDisable(true);
        confirmDeactivation.setVisible(true);
        cancelDeactivation.setVisible(true);
    }

    @FXML
    protected void deactivationConfirmed() {
        deactivationStatus.setFill(javafx.scene.paint.Color.RED);
        deactivationStatus.setText("Account deactivated.");
        deactivationButton.setDisable(false);
        confirmDeactivation.setVisible(false);
        cancelDeactivation.setVisible(false);
        user.setActive(false);
        new UserRepository().update(user);
        // TODO: Logout user
    }

    @FXML
    protected void deactivationCanceled() {
        deactivationStatus.setFill(javafx.scene.paint.Color.GREEN);
        deactivationStatus.setText("Deactivation cancelled.");
        deactivationButton.setDisable(false);
        confirmDeactivation.setVisible(false);
        cancelDeactivation.setVisible(false);
    }

    @Override
    public void update() {
        user = new UserRepository().getById(Login.getUserLoggedIn().orElse(1));
        address = new AddressRepository().getById(user.getAddressId());
        countryInput.setText(address.getCountry());
        cityInput.setText(address.getCity());
        streetInput.setText(address.getStreet());
        postalCodeInput.setText(address.getPostalCode());
        houseNumberInput.setText(address.getHouseNumber());
        flatNumberInput.setText(address.getFlatNumber());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        update();
    }
}
