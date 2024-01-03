package pap.controllers;

import javafx.fxml.FXML;

public class LoginScreenController implements UpdatableController {
    @FXML
    private UserLoginScreenController userLoginScreenController;
    @FXML
    private EmployeeLoginScreenController employeeLoginScreenController;
    
    @Override
    public void update() {
        userLoginScreenController.update();
        employeeLoginScreenController.update();
    }
}
