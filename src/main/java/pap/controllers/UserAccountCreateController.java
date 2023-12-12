package pap.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import pap.helpers.AccountManager;
import pap.helpers.LoadedPages;

import java.util.Arrays;


public class UserAccountCreateController implements UpdatableController {
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
        GlobalController.switchVisibleContent(this, LoadedPages.loginPage);
    }
    
    @FXML
    private void createAccountButtonPressed() {
        AccountManager.createAccount(
                nameInput, surnameInput, emailInput, countryInput, cityInput,
                streetInput, postalCodeInput, houseNumberInput, flatNumberInput,
                passwordInput, passwordConfirmation, passUnmatched, operationStatus
        );
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
