package pap.accounts;

import db.DAO.EmployeeDAO;
import db.Entities.Employee;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class EmployeeCreateAccount {
    @FXML
    private TextField userEmailInput;
    @FXML
    private PasswordField userPasswordInput;
    @FXML
    private TextField employeeUsernameInput;
    @FXML
    private PasswordField employeePasswordInput;
    @FXML
    private PasswordField employeePasswordConfirmation;
    @FXML
    private TextField roleInput;
    @FXML
    private TextField branchNameInput;
    @FXML
    private Text operationStatus;
    @FXML
    private Text passUnmached;

    @FXML
    protected void createEmployeeAccountPressed() {
        String userEmail = userEmailInput.getText();
        String userPassword = userPasswordInput.getText();
        String employeeUsername = employeeUsernameInput.getText();
        String employeePassword = employeePasswordInput.getText();
        String employeePasswordConf = employeePasswordConfirmation.getText();
        String role = roleInput.getText();
        String branch = branchNameInput.getText();

        if (userEmail.isEmpty() || userPassword.isEmpty() || employeeUsername.isEmpty() || employeePassword.isEmpty()
            || employeePasswordConf.isEmpty() || role.isEmpty() || branch.isEmpty()) {
            operationStatus.setText("All fields must be filled!");
            operationStatus.setVisible(true);
            return;
        }

        int uid = UserLoginScreen.tryLogin(userEmail, userPassword);
        if (uid == -1) {
            operationStatus.setText("Wrong user email or password!");
            return;
        }

        if (!employeePassword.equals(employeePasswordConf)) {
            passUnmached.setVisible(true);
            return;
        }
        passUnmached.setVisible(false);

        Employee emp = new Employee();

        String passwordSalt = PasswordHasher.generateSalt();
        emp.setPasswordSalt(passwordSalt);

        String passwordHash = PasswordHasher.hashPassword(employeePassword, passwordSalt);
        emp.setPasswordHash(passwordHash);

        emp.setUsername(employeeUsername);
        emp.setRole(role);

        //TODO
        // zdobywanie branch id po nazwie, na razie sztywne 1
        emp.setBranchId(1);

        new EmployeeDAO().create(emp);
        operationStatus.setText("Account created!");
        operationStatus.setFill(javafx.scene.paint.Color.GREEN);
        operationStatus.setVisible(true);
    }
}
