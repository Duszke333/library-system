package pap.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import pap.helpers.LoadedPages;

import java.util.Optional;

import static pap.helpers.Login.*;

public class EmployeeLoginScreenController implements UpdatableController {
    /**
     * A controller class for employee-login-screen page.
     */
    @FXML
    private TextField loginUsername;
    @FXML
    private PasswordField loginPassword;
    @FXML
    private Text loginStatus;

    @FXML
    protected void loginButtonPressed() {
        /*
            A method that tries to log in the user.
         */

        // Get the input data
        String username = loginUsername.getText();
        String password = loginPassword.getText();

        // Try to log in the user
        var id = tryLoginEmployee(username, password);
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
            loginStatus.setText("This account is deactivated");
            loginStatus.setVisible(true);
        }
        else {
            setEmployeeLoggedIn(Optional.of(id));
            GlobalController.switchVisibleContent(LoadedPages.employeeDashboard);
        }
    }

    @Override
    public void update() {
        loginUsername.clear();
        loginPassword.clear();
        loginStatus.setVisible(false);
        loginStatus.setFill(javafx.scene.paint.Color.RED);
    }
}
