package pap.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.text.Text;
import pap.db.Entities.Employee;
import pap.db.Repository.EmployeeRepository;
import pap.helpers.Login;
import pap.helpers.PasswordHasher;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeePasswordChangeController implements UpdatableController, Initializable {
    private Employee employee;
    @FXML
    private PasswordField passwordInput;
    @FXML
    private PasswordField newPasswordInput;
    @FXML
    private PasswordField newPasswordConfirmation;
    @FXML
    private Text passUnmatched;
    @FXML
    protected void changePassword() {
        passUnmatched.setFill(javafx.scene.paint.Color.RED);
        passUnmatched.setVisible(false);
        String password = passwordInput.getText();
        String newPassword = newPasswordInput.getText();
        String newPasswordConfirm = newPasswordConfirmation.getText();

        if (password.isEmpty() || newPassword.isEmpty() || newPasswordConfirm.isEmpty()) {
            passUnmatched.setText("All fields must be filled!");
            passUnmatched.setVisible(true);
            return;
        }

        if (!PasswordHasher.hashPassword(password, employee.getPasswordSalt()).equals(employee.getPasswordHash())) {
            passUnmatched.setText("Wrong password!");
            passUnmatched.setVisible(true);
            return;
        }

        if (!newPassword.equals(newPasswordConfirm)) {
            passUnmatched.setText("New passwords do not match!");
            passUnmatched.setVisible(true);
            return;
        }

        if (password.equals(newPassword)) {
            passUnmatched.setText("New password cannot be the same as old one!");
            passUnmatched.setVisible(true);
            return;
        }

        String salt = PasswordHasher.generateSalt();
        String hashedPassword = PasswordHasher.hashPassword(newPassword, salt);
        employee.setPasswordHash(hashedPassword);
        employee.setPasswordSalt(salt);

        new EmployeeRepository().update(employee);
        passwordInput.clear();
        newPasswordInput.clear();
        newPasswordConfirmation.clear();

        passUnmatched.setText("Password changed successfully!");
        passUnmatched.setFill(javafx.scene.paint.Color.GREEN);
        passUnmatched.setVisible(true);
    }
    @Override
    public void update() {
        employee = new EmployeeRepository().getById(Login.getEmployeeLoggedIn().orElse(1));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        update();
    }
}
