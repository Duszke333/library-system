package pap.helpers;

import lombok.Getter;
import lombok.NonNull;
import pap.controllers.GlobalController;
import pap.db.Entities.Employee;
import pap.db.Entities.User;
import pap.db.Repository.EmployeeRepository;
import pap.db.Repository.UserRepository;

import java.util.Optional;

/**
 * Helper class that lets users and employees log into their accounts.
 */
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

    /**
     * A function that tries to log in the user.
     * @param email Email of the user
     * @param password Credentials of the user
     * @return id of logged-in user
     * @throws InvalidCredentialsException upon incorrect login options
     */
    public static int tryLoginUser(String email, String password) throws InvalidCredentialsException {
        if (email.isBlank() || password.isBlank()) throw new InvalidCredentialsException("Empty credentials");

        User user = new UserRepository().getByEmail(email);
        if (user == null) throw new InvalidCredentialsException("No such user in database");

        String salt = user.getPasswordSalt();
        String hashedPassword = user.getPasswordHash();
        if (!hashedPassword.equals(PasswordHasher.hashPassword(password, salt))) throw new InvalidCredentialsException("Wrong password");

        if (!user.isActive()) throw new InvalidCredentialsException("This account is deactivated, please create a new one");
        
        return user.getAccountId();
    }

    /**
     * A function that tries to log in the employee.
     * @param username Username of the employee
     * @param password Credentials of the employee
     * @return id of logged-in employee
     * @throws InvalidCredentialsException upon incorrect login options
     */
    public static int tryLoginEmployee(String username, String password) throws InvalidCredentialsException {
        if (username.isBlank() || password.isBlank()) throw new InvalidCredentialsException("Empty credentials");

        Employee emp = new EmployeeRepository().getByUsername(username);
        if (emp == null) throw new InvalidCredentialsException("No such employee in database");

        String salt = emp.getPasswordSalt();
        String hashedPassword = emp.getPasswordHash();
        if (!hashedPassword.equals(PasswordHasher.hashPassword(password, salt))) throw new InvalidCredentialsException("Wrong password");

        if (!emp.isActive()) throw new InvalidCredentialsException("This account is deactivated");

        return emp.getEmployeeId();
    }
}
