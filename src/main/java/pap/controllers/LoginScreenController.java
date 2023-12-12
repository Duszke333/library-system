package pap.controllers;

import javafx.fxml.FXML;
import pap.helpers.LoadedPages;

public class LoginScreenController implements UpdatableController {
    @FXML
    private UserLoginScreenController userLoginScreenController;
    @FXML
    private EmployeeLoginScreenController employeeLoginScreenController;
    @Override
    public void update() {
        System.out.println("Update");
        userLoginScreenController.update();
        employeeLoginScreenController.update();
    }
}
