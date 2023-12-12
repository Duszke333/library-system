package pap.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.Optional;

import static pap.helpers.LoadedPages.createAccountPage;
import static pap.helpers.LoadedPages.userManagepage;
import static pap.helpers.Login.*;

public class UserLoginScreenController {
    @FXML
    private TextField loginEmail;
    @FXML
    private PasswordField loginPassword;
    @FXML
    private Text loginStatus;

    @FXML
    private void createAccountButtonPressed() {
        loginEmail.clear();
        loginPassword.clear();
        GlobalController.setContentPane(createAccountPage);
    }

    @FXML
    private void loginButtonPressed() {
        String email = loginEmail.getText().strip();
        String password = loginPassword.getText().strip();
//        System.out.printf("%s\t%s\n", email, password);
        
        var id = tryLogin(email, password);
        if (id == LoginTry.EmptyCredentials) {
            loginStatus.setText("All fields must be filled");
            loginStatus.setVisible(true);
        }
        else if (id == LoginTry.IncorrectPassword) {
            loginStatus.setText("Wrong password");
            loginStatus.setVisible(true);
        }
        else if (id == LoginTry.NoUser) {
            loginStatus.setText("No such user in database");
            loginStatus.setVisible(true);
        }
        else {
            setUserLoggedIn(Optional.of(id));
            loginEmail.clear();
            loginPassword.clear();
            loginStatus.setVisible(false);
            GlobalController.setContentPane(userManagepage);
        }
    }
}
