package pap.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import pap.db.Entities.Address;
import pap.db.Entities.User;
import pap.db.Repository.AddressRepository;
import pap.db.Repository.UserRepository;
import pap.helpers.ConstraintChecker;
import pap.helpers.LoadedPages;
import pap.helpers.PasswordHasher;

import java.util.Arrays;


public class UserAccountCreateController implements UpdatableController {
    /**
     * A controller class for user-account-create page.
     */
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
    private Text passUnmatched;
    @FXML
    private Text operationStatus;

    @FXML
    private void alreadyAccountButtonPressed() {
        GlobalController.switchVisibleContent(LoadedPages.loginScreen);
    }
    
    @FXML
    private void createAccountButtonPressed() {
        /*
            A method that creates a new user account.
         */
        operationStatus.setFill(javafx.scene.paint.Color.RED);

        // Get all the input data.
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

        // Check if all the fields are filled
        if (name.isEmpty() || email.isEmpty() || country.isEmpty() || city.isEmpty()
                || street.isEmpty() || postalCode.isEmpty() || houseNumber.isEmpty()
                || password.isEmpty() || passwordConfirm.isEmpty()) {
            operationStatus.setText("All fields must be filled!");
            operationStatus.setVisible(true);
            return;
        }

        // Check if the passwords match
        if (!password.equals(passwordConfirm)) {
            passUnmatched.setVisible(true);
            return;
        }

        passUnmatched.setVisible(false);

        // Create the address
        Address addr = new Address();
        addr.setCountry(country);
        addr.setCity(city);
        addr.setStreet(street);
        addr.setPostalCode(postalCode);
        addr.setHouseNumber(houseNumber);
        addr.setFlatNumber(flatNumber);

        // Check if the address is valid
        try {
            ConstraintChecker.checkAddress(addr);
        } catch (IllegalArgumentException e) {
            operationStatus.setText("Error: " + e.getMessage());
            operationStatus.setVisible(true);
            return;
        }

        // Create the user object
        User usr = new User();
        usr.setFirstName(name);
        usr.setLastName(surname);
        usr.setEmail(email);
        usr.setAddressId(addr.getAddressId());
        usr.setActive(true);
        usr.setDateCreated(new java.sql.Date(System.currentTimeMillis()));

        // create salt and hash the password
        String stringSalt = PasswordHasher.generateSalt();
        usr.setPasswordSalt(stringSalt);

        String hashedPassword = PasswordHasher.hashPassword(password, stringSalt);
        usr.setPasswordHash(hashedPassword);
        usr.setAddressId(addr.getAddressId());

        // Check if the user is valid
        UserRepository userRepo = new UserRepository();
        try {
            ConstraintChecker.checkUser(usr, userRepo);
        } catch (IllegalArgumentException e) {
            operationStatus.setText("Error: " + e.getMessage());
            operationStatus.setVisible(true);
            return;
        }

        // Create the address and the user account in the database
        new AddressRepository().create(addr);
        userRepo.create(usr);

        // inform the user about success
        operationStatus.setFill(javafx.scene.paint.Color.GREEN);
        operationStatus.setText("Account created!");
        operationStatus.setVisible(true);
    }

    @Override
    public void update() {
        for (TextField textField : Arrays.asList(nameInput, surnameInput, emailInput, countryInput, cityInput, streetInput, postalCodeInput, houseNumberInput, flatNumberInput)) {
            textField.clear();
        }
        passwordInput.clear();
        passwordConfirmation.clear();
        passUnmatched.setVisible(false);
        operationStatus.setVisible(false);
        operationStatus.setFill(javafx.scene.paint.Color.RED);
    }
}
