package pap.helpers;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import pap.db.Entities.User;
import pap.db.Repository.UserRepository;

import java.util.Optional;

public class Login {
    private Login() {}
    
    @Getter
    @Setter
    private static Optional<Integer> userLoggedIn = Optional.empty();
    
    @Data
    public static class LoginTry {
        public static int EmptyCredentials = -1;
        public static int NoUser = -2;
        public static int IncorrectPassword = -3;
    }
    
    public static int tryLogin(String email, String password) {
        if (email.isBlank() || password.isBlank()) {
            return LoginTry.EmptyCredentials;
        }

        User user = new UserRepository().getByEmail(email);
        if (user == null) {
            return LoginTry.NoUser;
        }

        String salt = user.getPasswordSalt();
        String hashedPassword = user.getPasswordHash();
        if (!hashedPassword.equals(PasswordHasher.hashPassword(password, salt))) {
            return LoginTry.IncorrectPassword;
        }
        
        return user.getAccountId();
    }
}
