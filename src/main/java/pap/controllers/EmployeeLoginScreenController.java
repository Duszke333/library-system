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
        int id;
        try {
            id = tryLoginEmployee(username, password);
        } catch (IllegalArgumentException e) {
            loginStatus.setText("Error: " + e.getMessage());
            loginStatus.setVisible(true);
            return;
        }

        // Log the employee in and switch to user-dashboard page
        setEmployeeLoggedIn(Optional.of(id));
        GlobalController.switchVisibleContent(LoadedPages.employeeDashboard);
    }

    @Override
    public void update() {
        loginUsername.clear();
        loginPassword.clear();
        loginStatus.setVisible(false);
        loginStatus.setFill(javafx.scene.paint.Color.RED);
    }
}
