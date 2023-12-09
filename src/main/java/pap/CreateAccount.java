package pap;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

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
    private PasswordField passwordInput;
    @FXML
    private PasswordField passwordConfirmation;
    @FXML
    private Text passUnmached;
    @FXML
    private Text operationStatus;
    @FXML
    protected void creationConfirmed() {
        // get all data from inputs
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
        String passwordConfirm = passwordConfirmation.getText();

        // check if all fields are not empty
        if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || country.isEmpty() || city.isEmpty()
                || street.isEmpty() || postalCode.isEmpty() || houseNumber.isEmpty() || flatNumber.isEmpty()
                || password.isEmpty() || passwordConfirm.isEmpty()) {
            System.out.println("All fields must be filled!");
            operationStatus.setVisible(true);
            return;
        }

        if (password.equals(passwordConfirm)) {
            passUnmached.setVisible(false);
            operationStatus.setFill(javafx.scene.paint.Color.GREEN);
            operationStatus.setText("Account created!");
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
            passUnmached.setVisible(true);
            System.out.println("Passwords do not match!");
        }
    }
}
