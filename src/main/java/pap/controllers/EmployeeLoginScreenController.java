package pap.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import pap.helpers.LoadedPages;
import pap.helpers.Login;

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
        GlobalController.switchVisibleContent(this, LoadedPages.employeeManagePage);
    }

    @FXML
    protected void loginButtonPressed() {
        String username = loginUsername.getText();
        String password = loginPassword.getText();

        var id = tryLoginEmployee(username, password);
        if (id == Login.LoginTry.EmptyCredentials) {
            loginStatus.setText("All fields must be filled");
            loginStatus.setVisible(true);
        }
        else if (id == Login.LoginTry.IncorrectPassword) {
            loginStatus.setText("Wrong password");
            loginStatus.setVisible(true);
        }
        else if (id == Login.LoginTry.NoUser) {
            loginStatus.setText("No such user in database");
            loginStatus.setVisible(true);
        }
        else {
            // TODO: make missing fields and panels
//            setEmployeeLoggedIn(Optional.of(id));
//            GlobalController.setContentPane(employeeManagepage);
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
