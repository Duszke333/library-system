package pap.controllers;

import pap.db.Entities.Employee;
import pap.db.Repository.EmployeeRepository;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import pap.helpers.PasswordHasher;

public class EmployeeLoginScreenController implements Updateable {
    @FXML
    private TextField loginUsername;
    @FXML
    private PasswordField loginPassword;
    @FXML
    private Text loginStatus;

    @FXML
    protected void handleLoginButtonAction() {
        String username = loginUsername.getText();
        String password = loginPassword.getText();
        if (username.isEmpty() || password.isEmpty()) {
            loginStatus.setText("All fields must be filled!");
            loginStatus.setVisible(true);
            return;
        }

        Employee employee = new EmployeeRepository().getByUsername(username);

        if (employee == null) {
            loginStatus.setText("Wrong username or password!");
            loginStatus.setVisible(true);
            return;
        }

        String salt = employee.getPasswordSalt();
        String hashedPassword = employee.getPasswordHash();
        String hashedInput = PasswordHasher.hashPassword(password, salt);
        if (!hashedPassword.equals(hashedInput)) {
            loginStatus.setText("Wrong username or password!");
            loginStatus.setVisible(true);
            return;
        }

        loginStatus.setText("Logged in!");
        loginStatus.setFill(javafx.scene.paint.Color.GREEN);
        loginStatus.setVisible(true);
    }

    @Override
    public void update() {
        loginUsername.clear();
        loginPassword.clear();
        loginStatus.setVisible(false);
        loginStatus.setFill(javafx.scene.paint.Color.RED);
    }
}
