package pap.helpers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class LoginTest {
    @Test
    void testTryLoginUserEmptyCredentials() {
        String email = "SomeEmail@gmail.com";
        String password = "test";
        String empty = "";
        InvalidCredentialsException e = assertThrows(InvalidCredentialsException.class, () -> Login.tryLoginUser(email, empty));
        assert e.getMessage().equals("Empty credentials");
        e = assertThrows(InvalidCredentialsException.class, () -> Login.tryLoginUser(empty, password));
        assert e.getMessage().equals("Empty credentials");
        e = assertThrows(InvalidCredentialsException.class, () -> Login.tryLoginUser(empty, empty));
        assert e.getMessage().equals("Empty credentials");
    }

    @Test
    void testTryLoginEmployeeEmptyCredentials() {
        String username = "SomeUserName";
        String password = "test";
        String empty = "";
        InvalidCredentialsException e = assertThrows(InvalidCredentialsException.class, () -> Login.tryLoginEmployee(username, empty));
        assert e.getMessage().equals("Empty credentials");
        e = assertThrows(InvalidCredentialsException.class, () -> Login.tryLoginEmployee(empty, password));
        assert e.getMessage().equals("Empty credentials");
        e = assertThrows(InvalidCredentialsException.class, () -> Login.tryLoginEmployee(empty, empty));
        assert e.getMessage().equals("Empty credentials");
    }
}