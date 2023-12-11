package pap;

import db.DAO.AddressDAO;
import db.DAO.UserDAO;
import db.Entities.Address;
import db.Entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

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
    protected void creationConfirmed() throws NoSuchAlgorithmException {
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

        if (password.equals(passwordConfirm)) {
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
            byte[] salt = new byte[16];
            new SecureRandom().nextBytes(salt);

            StringBuilder sb = new StringBuilder();
            for (byte b : salt) {
                sb.append(String.format("%02x", b));
            }
            String stringSalt = sb.toString();
            usr.setPasswordSalt(stringSalt);

            //String hashedPassword = PasswordHasher.hashPassword(password, stringSalt);
            //usr.setPasswordHash(hashedPassword);
            usr.setAddressId(addr.getAddressId());

            new UserDAO().create(usr);

        } else {
            passUnmached.setVisible(true);
            System.out.println("Passwords do not match!");
        }
    }
}
