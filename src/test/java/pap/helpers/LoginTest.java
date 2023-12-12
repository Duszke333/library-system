package pap.helpers;

import org.junit.jupiter.api.Test;

class LoginTest {
    @Test
    void testTryLoginUserEmptyCredentials() {
        String email = "SomeEmail@gmail.com";
        String password = "test";
        String empty = "";
        assert Login.tryLoginUser(email, empty) == Login.LoginTry.EmptyCredentials;
        assert Login.tryLoginUser(empty, password) == Login.LoginTry.EmptyCredentials;
        assert Login.tryLoginUser(empty, empty) == Login.LoginTry.EmptyCredentials;
    }

    @Test
    void testTryLoginEmployeeEmptyCredentials() {
        String username = "SomeUserName";
        String password = "test";
        String empty = "";
        assert Login.tryLoginEmployee(username, empty) == Login.LoginTry.EmptyCredentials;
        assert Login.tryLoginEmployee(empty, password) == Login.LoginTry.EmptyCredentials;
        assert Login.tryLoginEmployee(empty, empty) == Login.LoginTry.EmptyCredentials;
    }
}