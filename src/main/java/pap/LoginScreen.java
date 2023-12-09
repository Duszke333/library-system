package pap;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class LoginScreen {
    @FXML
    private TextField loginInput;
    @FXML
    private PasswordField passwordInput;
    @FXML
    private Button loginButton;
    @FXML
    private Button createAccountButton;
    @FXML
    private Text loginError;

    @FXML
    protected void loginPressed() {
        // get login and password from inputs
        String login = loginInput.getText();
        String password = passwordInput.getText();

        if (login.isEmpty() || password.isEmpty()) {
            loginError.setVisible(true);
            return;
        }


    }
}
