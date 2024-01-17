package pap.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import pap.helpers.InvalidCredentialsException;
import pap.helpers.LoadedPages;

import java.util.Optional;

import static pap.helpers.Login.*;

public class EmployeeLoginScreenController implements UpdatableController {
    /**
     * A controller class for employee-login-screen page which allows an employee to log into his account.
     */
    @FXML
    private TextField loginUsername;
    @FXML
    private PasswordField loginPassword;
    @FXML
    private Text loginStatus;

    /**
     * A method that tries to log in the employee.
     */
    @FXML
    protected void loginButtonPressed() {
        // Get the input data
        String username = loginUsername.getText();
        String password = loginPassword.getText();

        // Try to log in the employee
        int id;
        try {
            id = tryLoginEmployee(username, password);
        } catch (InvalidCredentialsException e) {
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
