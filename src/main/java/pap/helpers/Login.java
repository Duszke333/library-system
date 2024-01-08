package pap.helpers;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import pap.controllers.GlobalController;
import pap.db.Entities.Employee;
import pap.db.Entities.User;
import pap.db.Repository.EmployeeRepository;
import pap.db.Repository.UserRepository;

import java.util.Optional;

public class Login {
    private Login() {}
    
    @Getter
    @NonNull
    private static Optional<Integer> userLoggedIn = Optional.empty();
    
    public static void setUserLoggedIn(@NonNull Optional<Integer> user) {
        userLoggedIn = user;
        GlobalController.getParent().update();
    }

    @Getter
    @NonNull
    private static Optional<Integer> employeeLoggedIn = Optional.empty();
    
    public static void setEmployeeLoggedIn(@NonNull Optional<Integer> employee) {
        employeeLoggedIn = employee;
        GlobalController.getParent().update();
    }
    
    @Data
    public static class LoginTry {
        public static int EmptyCredentials = -1;
        public static int NoUser = -2;
        public static int IncorrectPassword = -3;
        public static int Deactivated = -4;
    }
    
    public static int tryLoginUser(String email, String password) {
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

        if (!user.isActive()) {
            return LoginTry.Deactivated;
        }
        
        return user.getAccountId();
    }

    public static int tryLoginEmployee(String username, String password) {
        if (username.isBlank() || password.isBlank()) {
            return LoginTry.EmptyCredentials;
        }

        Employee emp = new EmployeeRepository().getByUsername(username);
        if (emp == null) {
            return LoginTry.NoUser;
        }

        String salt = emp.getPasswordSalt();
        String hashedPassword = emp.getPasswordHash();
        if (!hashedPassword.equals(PasswordHasher.hashPassword(password, salt))) {
            return LoginTry.IncorrectPassword;
        }

        if (!emp.isActive()) {
            return LoginTry.Deactivated;
        }

        return emp.getEmployeeId();
    }
}
