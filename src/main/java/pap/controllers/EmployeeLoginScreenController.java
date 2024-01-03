package pap.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import pap.helpers.LoadedPages;

import java.util.Optional;

import static pap.helpers.Login.*;

public class EmployeeLoginScreenController implements UpdatableController {
    @FXML
    private TextField loginUsername;
    @FXML
    private PasswordField loginPassword;
    @FXML
    private Text loginStatus;
    
    @FXML
    protected void createAccountButtonPressed() {
        GlobalController.switchVisibleContent(LoadedPages.employeeAccountCreateController, LoadedPages.employeeAccountCreate);
    }

    @FXML
    protected void loginButtonPressed() {
        String username = loginUsername.getText();
        String password = loginPassword.getText();

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
            GlobalController.switchVisibleContent(LoadedPages.employeeDashboardController, LoadedPages.employeeDashboard);
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
