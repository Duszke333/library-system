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
import pap.helpers.ConstraintChecker;
import pap.helpers.LoadedPages;
import pap.helpers.Login;
import pap.helpers.PasswordHasher;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserAccountManageController implements UpdatableController, Initializable {
    /**
     * A controller class for user-account-manage page.
     */
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
    protected void changePassword() {
        /*
            A method that changes the user's password.
         */
        passUnmatched.setFill(javafx.scene.paint.Color.RED);
        passUnmatched.setVisible(false);

        // Get the input data
        String password = passwordInput.getText();
        String newPassword = newPasswordInput.getText();
        String newPasswordConfirm = newPasswordConfirmation.getText();

        // Check if all the fields are filled
        if (password.isEmpty() || newPassword.isEmpty() || newPasswordConfirm.isEmpty()) {
            passUnmatched.setText("All fields must be filled!");
            passUnmatched.setVisible(true);
            return;
        }

        // Check if the password is correct
        if (!PasswordHasher.hashPassword(password, user.getPasswordSalt()).equals(user.getPasswordHash())) {
            passUnmatched.setText("Wrong password!");
            passUnmatched.setVisible(true);
            return;
        }

        // Check if the new passwords match
        if (!newPassword.equals(newPasswordConfirm)) {
            passUnmatched.setText("New passwords do not match!");
            passUnmatched.setVisible(true);
            return;
        }

        // Check if the new password is the same as the old one
        if (password.equals(newPassword)) {
            passUnmatched.setText("New password cannot be the same as old one!");
            passUnmatched.setVisible(true);
            return;
        }

        // Change the password
        String salt = PasswordHasher.generateSalt();
        String hashedPassword = PasswordHasher.hashPassword(newPassword, salt);
        user.setPasswordHash(hashedPassword);
        user.setPasswordSalt(salt);

        new UserRepository().update(user);

        // Clear the input fields
        passwordInput.clear();
        newPasswordInput.clear();
        newPasswordConfirmation.clear();

        // Show the success message
        passUnmatched.setText("Password changed successfully!");
        passUnmatched.setFill(javafx.scene.paint.Color.GREEN);
        passUnmatched.setVisible(true);
    }

    @FXML
    protected void updateAddress() {
        /*
            A method that updates the user's address.
         */
        updateStatus.setFill(javafx.scene.paint.Color.RED);
        updateStatus.setVisible(false);

        // Get the input data
        String country = countryInput.getText();
        String city = cityInput.getText();
        String street = streetInput.getText();
        String postalCode = postalCodeInput.getText();
        String houseNumber = houseNumberInput.getText();
        String flatNumber = flatNumberInput.getText();

        // Check if all the required fields are filled
        if (country.isEmpty() || city.isEmpty() || street.isEmpty() || postalCode.isEmpty() || houseNumber.isEmpty()) {
            updateStatus.setText("All fields except flat number must be filled!");
            updateStatus.setVisible(true);
            return;
        }

        // Update the address object
        address.setCountry(country);
        address.setCity(city);
        address.setStreet(street);
        address.setPostalCode(postalCode);
        address.setHouseNumber(houseNumber);
        address.setFlatNumber(flatNumber);

        // Check if the address is correct
        int error = ConstraintChecker.checkAddress(address);
        if (error != -1) {
            updateStatus.setText("Error: " + ConstraintChecker.AddressErrors.values()[error].toString());
            updateStatus.setVisible(true);
            return;
        }

        // Update the address
        new AddressRepository().update(address);

        // Show the success message
        updateStatus.setText("Address updated successfully!");
        updateStatus.setFill(javafx.scene.paint.Color.GREEN);
        updateStatus.setVisible(true);
    }

    @Deprecated
    @FXML
    protected void deactivateAccount() {

        deactivationStatus.setFill(javafx.scene.paint.Color.WHITE);
        deactivationStatus.setText("Are you sure? Please confirm.");
        deactivationStatus.setVisible(true);
        deactivationButton.setDisable(true);
        confirmDeactivation.setVisible(true);
        cancelDeactivation.setVisible(true);
    }

    @Deprecated
    @FXML
    protected void deactivationConfirmed() {
        deactivationStatus.setFill(javafx.scene.paint.Color.RED);
        deactivationStatus.setText("Account deactivated.");
        deactivationButton.setDisable(false);
        confirmDeactivation.setVisible(false);
        cancelDeactivation.setVisible(false);
        user.setActive(false);
        new UserRepository().update(user);
    }

    @Deprecated
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
