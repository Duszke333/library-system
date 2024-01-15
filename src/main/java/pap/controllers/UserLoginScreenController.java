package pap.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import pap.helpers.LoadedPages;

import java.util.Optional;

import static pap.helpers.Login.*;

public class UserLoginScreenController implements UpdatableController {
    /*
        A controller class for user-login-screen page.
     */
    @FXML
    private TextField loginEmail;
    @FXML
    private PasswordField loginPassword;
    @FXML
    private Text loginStatus;

    @FXML
    private void createAccountButtonPressed() {
        /*
            A method that switches to user-account-create page which allows to create an account.
         */
        loginEmail.clear();
        loginPassword.clear();
        GlobalController.switchVisibleContent(LoadedPages.userAccountCreate);
    }

    @FXML
    private void loginButtonPressed() {
        /*
            A method that tries to log in the user.
         */

        // Get the input data
        String email = loginEmail.getText().strip();
        String password = loginPassword.getText().strip();

        // Try to log in the user
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
        else if (id == LoginTry.Deactivated) {
            loginStatus.setText("This account is deactivated, please create a new one");
            loginStatus.setVisible(true);
        }
        else {
            setUserLoggedIn(Optional.of(id));
            GlobalController.switchVisibleContent(LoadedPages.userDashboard);
        }
    }

    @Override
    public void update() {
        loginEmail.clear();
        loginPassword.clear();
        loginStatus.setVisible(false);
    }
}
