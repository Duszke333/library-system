package pap.Accounts;

import db.DAO.UserDAO;
import db.Entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class UserLoginScreen {
    @FXML
    private TextField loginEmail;
    @FXML
    private PasswordField loginPassword;
    @FXML
    private Button createAccountButton;
    @FXML
    private Text loginStatus;

    @FXML
    protected void loginPressed() {
        // get login and password from inputs
        String email = loginEmail.getText();
        String password = loginPassword.getText();

        if (email.isEmpty() || password.isEmpty()) {
            loginStatus.setVisible(true);
            return;
        }

        // bedzie po mailu, na razie test czy dziala
        User usr = new UserDAO().read(3);
        if (usr == null) {
            System.out.println("NO USER");
            loginStatus.setVisible(true);
            return;
        }

        String salt = usr.getPasswordSalt();
        String hashedPassword = usr.getPasswordHash();

        if (hashedPassword.equals(PasswordHasher.hashPassword(password, salt))) {
            loginStatus.setText("Logged in!");
            loginStatus.setFill(javafx.scene.paint.Color.GREEN);
            loginStatus.setVisible(true);
            System.out.println("Zalogowano");
        } else {
            System.out.println("FAIL");
            loginStatus.setVisible(true);
        }
    }
}
