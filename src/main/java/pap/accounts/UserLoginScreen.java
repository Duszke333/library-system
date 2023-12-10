package pap.accounts;

import db.DAO.UserDAO;
import db.Entities.User;
import db.Repository.UserRepository;
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

        int id = tryLogin(email, password);

        switch (id) {
            case -2:
                loginStatus.setText("All fields must be filled!");
                loginStatus.setVisible(true);
                break;
            case -1:
                loginStatus.setText("Wrong email or password!");
                loginStatus.setVisible(true);
                break;
            default:
                loginStatus.setText("Logged in!");
                loginStatus.setFill(javafx.scene.paint.Color.GREEN);
                loginStatus.setVisible(true);
                System.out.println("Zalogowano");
                break;
        }
    }

    public static int tryLogin(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            return -2;
        }

        User usr = new UserRepository().getByEmail(email);
        if (usr == null) {
            System.out.println("NO USER");
            return -1;
        }

        String salt = usr.getPasswordSalt();
        String hashedPassword = usr.getPasswordHash();

        if (hashedPassword.equals(PasswordHasher.hashPassword(password, salt))) {
            return usr.getAccountId();
        } else {
            return -1;
        }
    }
}
