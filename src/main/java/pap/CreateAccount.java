package pap;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class CreateAccount {
    @FXML
    private TextField nameInput;
    @FXML
    private TextField surnameInput;
    @FXML
    private TextField emailInput;
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
    private Button confirmAccountCreation;
    @FXML
    private PasswordField passwordInput;
    @FXML
    private PasswordField passwordConfirmation;

    @FXML
    protected void creationConfirmed() {
        String name = nameInput.getText();
        String surname = surnameInput.getText();
        String email = emailInput.getText();
        String country = countryInput.getText();
        String city = cityInput.getText();
        String street = streetInput.getText();
        String postalCode = postalCodeInput.getText();
        String houseNumber = houseNumberInput.getText();
        String flatNumber = flatNumberInput.getText();
        String password = passwordInput.getText();
        String passwordConfirmation = this.passwordConfirmation.getText();
        if (password.equals(passwordConfirmation)) {
            System.out.println("Account created!");
            System.out.println("Name: " + name);
            System.out.println("Surname: " + surname);
            System.out.println("Email: " + email);
            System.out.println("Country: " + country);
            System.out.println("City: " + city);
            System.out.println("Street: " + street);
            System.out.println("Postal code: " + postalCode);
            System.out.println("House number: " + houseNumber);
            System.out.println("Flat number: " + flatNumber);
            System.out.println("Password: " + password);
        } else {
            System.out.println("Passwords do not match!");
        }
    }
}
