package pap.controllers;

import pap.db.Entities.Branch;
import pap.db.Entities.Employee;
import pap.db.Repository.BranchRepository;
import pap.db.Repository.EmployeeRepository;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import static pap.helpers.Login.*;

import pap.helpers.ConstraintChecker;
import pap.helpers.ConstraintViolationException;
import pap.helpers.LoadedPages;
import pap.helpers.PasswordHasher;

public class EmployeeAccountCreateController implements UpdatableController {
    /**
     * A controller class for employee-account-create page which allows an employee
     * to create an account for another employee.
     */
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
        GlobalController.switchVisibleContent(LoadedPages.userAccountCreate);
    }

    /**
     * A method that creates a new employee account.
     */
    @FXML
    protected void createEmployeeAccountPressed() {
        // Get all the input data.
        String userEmail = userEmailInput.getText();
        String userPassword = userPasswordInput.getText();
        String employeeUsername = employeeUsernameInput.getText();
        String employeePassword = employeePasswordInput.getText();
        String employeePasswordConf = employeePasswordConfirmation.getText();
        String role = roleInput.getText();
        String branch = branchNameInput.getText();

        // Check if all the fields are filled
        if (userEmail.isEmpty() || userPassword.isEmpty() || employeeUsername.isEmpty() || employeePassword.isEmpty()
            || employeePasswordConf.isEmpty() || role.isEmpty() || branch.isEmpty()) {
            operationStatus.setText("All fields must be filled!");
            operationStatus.setVisible(true);
            return;
        }

        // Check if the user account credentials are valid
        int uid;
        try {
          uid = tryLoginUser(userEmail, userPassword);
        } catch (IllegalArgumentException e) {
            operationStatus.setText("Error: " + e.getMessage());
            operationStatus.setVisible(true);
            return;
        }

        // Check if the employee account passwords match
        if (!employeePassword.equals(employeePasswordConf)) {
            passUnmatched.setText("Passwords do not match!");
            passUnmatched.setVisible(true);
            return;
        }
        passUnmatched.setVisible(false);

        // Create the employee account
        Employee emp = new Employee();

        // create salt and hash the password
        String passwordSalt = PasswordHasher.generateSalt();
        emp.setPasswordSalt(passwordSalt);

        String passwordHash = PasswordHasher.hashPassword(employeePassword, passwordSalt);
        emp.setPasswordHash(passwordHash);

        // fill all the account data
        emp.setUsername(employeeUsername);
        emp.setRole(role);
        emp.setDateCreated(new java.sql.Date(System.currentTimeMillis()));
        emp.setUserID(uid);

        // check if the branch exists
        Branch br = new BranchRepository().getByBranchName(branch);
        if (br == null) {
            operationStatus.setText("Branch under given name not found!");
            operationStatus.setVisible(true);
            return;
        }

        emp.setBranchId(br.getBranchId());

        // check if the employee account data is valid, and create the account if it is
        EmployeeRepository empRepo = new EmployeeRepository();
        try {
            ConstraintChecker.checkEmployee(emp, empRepo);
        } catch (ConstraintViolationException e) {
            operationStatus.setText("Error: " + e.getMessage());
            operationStatus.setVisible(true);
            return;
        }
        empRepo.create(emp);

        // inform the employee about success
        operationStatus.setText("Account created!");
        operationStatus.setFill(javafx.scene.paint.Color.GREEN);
        operationStatus.setVisible(true);
    }

    @Override
    public void update() {
        userEmailInput.clear();
        userPasswordInput.clear();
        employeeUsernameInput.clear();
        employeePasswordInput.clear();
        employeePasswordConfirmation.clear();
        roleInput.clear();
        branchNameInput.clear();
        operationStatus.setVisible(false);
        operationStatus.setFill(javafx.scene.paint.Color.RED);
        passUnmatched.setVisible(false);
    }
}
