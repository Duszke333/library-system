package pap.controllers;

import pap.db.DAO.AddressDAO;
import pap.db.DAO.UserDAO;
import pap.db.Entities.Address;
import pap.db.Entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import pap.helpers.PasswordHasher;

import static pap.helpers.LoadedPages.userLoginPage;

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
    private Text passUnmached;
    @FXML
    private Text operationStatus;

    @FXML
    private void alreadyAccountButtonPressed() {
        GlobalController.setContentPane(userLoginPage);
    }
    
    @FXML
    private void createAccountButtonPressed() {
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
                || street.isEmpty() || postalCode.isEmpty() || houseNumber.isEmpty()
                || password.isEmpty() || passwordConfirm.isEmpty()) {
            operationStatus.setVisible(true);
            return;
        }

        if (!password.equals(passwordConfirm)) {
            passUnmached.setVisible(true);
            return;
        }
        
        passUnmached.setVisible(false);
        operationStatus.setFill(javafx.scene.paint.Color.GREEN);
        operationStatus.setText("Account created!");
        operationStatus.setVisible(true);

        Address addr = new Address();
        addr.setCountry(country);
        addr.setCity(city);
        addr.setStreet(street);
        addr.setPostalCode(postalCode);
        addr.setHouseNumber(houseNumber);
        addr.setFlatNumber(flatNumber);

        new AddressDAO().create(addr);
        User usr = new User();
        usr.setFirstName(name);
        usr.setLastName(surname);
        usr.setEmail(email);
        usr.setAddressId(addr.getAddressId());
        usr.setActive(true);
        usr.setDateCreated(new java.sql.Date(System.currentTimeMillis()));

        // generate password salt
        String stringSalt = PasswordHasher.generateSalt();
        usr.setPasswordSalt(stringSalt);

        String hashedPassword = PasswordHasher.hashPassword(password, stringSalt);
        usr.setPasswordHash(hashedPassword);
        usr.setAddressId(addr.getAddressId());

        new UserDAO().create(usr);
    }

    @Override
    public void update() {
        nameInput.clear();
        surnameInput.clear();
        emailInput.clear();
        countryInput.clear();
        cityInput.clear();
        streetInput.clear();
        postalCodeInput.clear();
        houseNumberInput.clear();
        flatNumberInput.clear();
        passwordInput.clear();
        passwordConfirmation.clear();
        passUnmached.setVisible(false);
        operationStatus.setVisible(false);
        operationStatus.setFill(javafx.scene.paint.Color.RED);
    }
}
