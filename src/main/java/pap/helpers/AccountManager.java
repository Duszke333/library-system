package pap.helpers;

import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import pap.db.DAO.AddressDAO;
import pap.db.DAO.UserDAO;
import pap.db.Entities.Address;
import pap.db.Entities.User;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AccountManager {
    private AccountManager() {}
    
    public static synchronized Future<Void> createAccount(
            TextField nameInput, TextField surnameInput, TextField emailInput, TextField countryInput, TextField cityInput,
            TextField streetInput, TextField postalCodeInput, TextField houseNumberInput, TextField flatNumberInput,
            TextField passwordInput, TextField passwordConfirmation, Text passUnmatched, Text operationStatus
    ) {
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
            operationStatus.setVisible(true);
            return null;
        }

        if (!password.equals(passwordConfirm)) {
            passUnmatched.setVisible(true);
            return null;
        }

        passUnmatched.setVisible(false);
        operationStatus.setFill(javafx.scene.paint.Color.GREEN);
        operationStatus.setText("Account created!");
        operationStatus.setVisible(true);

        return Executors.newCachedThreadPool().submit(() -> {
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

            String stringSalt = PasswordHasher.generateSalt();
            usr.setPasswordSalt(stringSalt);

            String hashedPassword = PasswordHasher.hashPassword(password, stringSalt);
            usr.setPasswordHash(hashedPassword);
            usr.setAddressId(addr.getAddressId());
            new UserDAO().create(usr);
            
            return null;
        });
    }
}
