package pap.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import pap.helpers.LoadedPages;

import java.util.Optional;

import static pap.helpers.Login.*;

public class UserLoginScreenController implements UpdatableController {
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
        GlobalController.switchVisibleContent(LoadedPages.userAccountCreateController, LoadedPages.userAccountCreate);
    }

    @FXML
    private void loginButtonPressed() {
        String email = loginEmail.getText().strip();
        String password = loginPassword.getText().strip();
        
        var id = tryLoginUser(email, password);
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
            GlobalController.switchVisibleContent(LoadedPages.userDashboardController, LoadedPages.userDashboard);
        }
    }

    @Override
    public void update() {
        loginEmail.clear();
        loginPassword.clear();
        loginStatus.setVisible(false);
    }
}
