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


public class UserAccountCreateController implements Updateable {
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
        GlobalController.setContentPane(LoadedPages.loginPage);
    }
    
    @FXML
    private void createAccountButtonPressed() {
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

        if (name.isEmpty() || email.isEmpty() || country.isEmpty() || city.isEmpty()
                || street.isEmpty() || postalCode.isEmpty() || houseNumber.isEmpty()
                || password.isEmpty() || passwordConfirm.isEmpty()) {
            operationStatus.setText("All fields must be filled!");
            operationStatus.setVisible(true);
            return;
        }

        if (!password.equals(passwordConfirm)) {
            passUnmatched.setVisible(true);
            return;
        }

        passUnmatched.setVisible(false);

        Address addr = new Address();
        addr.setCountry(country);
        addr.setCity(city);
        addr.setStreet(street);
        addr.setPostalCode(postalCode);
        addr.setHouseNumber(houseNumber);
        addr.setFlatNumber(flatNumber);

        int error = ConstraintChecker.checkAddress(addr);
        if (error != -1) {
            operationStatus.setText("Error: " + ConstraintChecker.AddressErrors.values()[error].toString());
            operationStatus.setVisible(true);
            return;
        }
        new AddressRepository().create(addr);

        User usr = new User();
        usr.setFirstName(name);
        usr.setLastName(surname);
        usr.setEmail(email);
        usr.setAddressId(addr.getAddressId());
        usr.setActive(true);
        usr.setDateCreated(new java.sql.Date(System.currentTimeMillis()));

        String stringSalt = PasswordHasher.generateSalt();
        usr.setPasswordSalt(stringSalt);

        String hashedPassword = PasswordHasher.hashPassword(password, stringSalt);
        usr.setPasswordHash(hashedPassword);
        usr.setAddressId(addr.getAddressId());

        UserRepository userRepo = new UserRepository();
        error = ConstraintChecker.checkUser(usr, userRepo);
        if (error != -1) {
            operationStatus.setText("Error: " + ConstraintChecker.UserErrors.values()[error].toString());
            operationStatus.setVisible(true);
            return;
        }
        userRepo.create(usr);

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
