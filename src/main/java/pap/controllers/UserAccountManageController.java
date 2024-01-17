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
import pap.helpers.*;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserAccountManageController implements UpdatableController, Initializable {
    /**
     * A controller class for user-account-manage page which allows a user
     * to change information about his account.
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

    /**
     * A method that changes the user's password.
     */
    @FXML
    protected void changePassword() {
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

    /**
     * A method that updates the user's address.
     */
    @FXML
    protected void updateAddress() {
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
        try {
            ConstraintChecker.checkAddress(address);
        } catch (ConstraintViolationException e) {
            updateStatus.setText("Error: " + e.getMessage());
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
