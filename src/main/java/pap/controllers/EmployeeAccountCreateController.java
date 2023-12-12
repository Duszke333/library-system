package pap.controllers;

import pap.db.Entities.Employee;
import pap.db.Repository.EmployeeRepository;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import static pap.helpers.Login.*;

import pap.helpers.LoadedPages;
import pap.helpers.PasswordHasher;

public class EmployeeAccountCreateController {
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
    private Text passUnmatched;
    
    @FXML
    protected void createUserButtonPressed() {
        GlobalController.setContentPane(LoadedPages.createAccountPage);
    }

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

        int uid = tryLogin(userEmail, userPassword);
        if (uid == LoginTry.IncorrectPassword) {
            operationStatus.setText("Wrong user email or password!");
            return;
        }

        if (!employeePassword.equals(employeePasswordConf)) {
            passUnmatched.setVisible(true);
            return;
        }
        passUnmatched.setVisible(false);

        Employee emp = new Employee();

        String passwordSalt = PasswordHasher.generateSalt();
        emp.setPasswordSalt(passwordSalt);

        String passwordHash = PasswordHasher.hashPassword(employeePassword, passwordSalt);
        emp.setPasswordHash(passwordHash);

        emp.setUsername(employeeUsername);
        emp.setRole(role);
        emp.setDateCreated(new java.sql.Date(System.currentTimeMillis()));
        emp.setUserID(uid);

        //TODO
        // zdobywanie branch id po nazwie, na razie sztywne 1
        emp.setBranchId(1);

        new EmployeeRepository().create(emp);
        operationStatus.setText("Account created!");
        operationStatus.setFill(javafx.scene.paint.Color.GREEN);
        operationStatus.setVisible(true);
    }
}
