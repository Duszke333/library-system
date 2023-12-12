package pap.helpers;

import org.junit.jupiter.api.Test;


class PasswordHasherTest {
    @Test
    void testHashPassword() {
        String password = "test";
        String salt = "410472b22ad87e4565fc077b7c3f0511";
        String hashedPassword = "18cca6183e11dc2a07bf5f63509f7beadd23b99ec727dc1b4290072315484210fbca55e07b6cc524fdc37f6558f284fee43a9e6f5cb851b4062e633faff2db09";
        assert hashedPassword.equals(PasswordHasher.hashPassword(password, salt));
    }

    @Test
    void testHashPasswordIncorrectSalt() {
        String password = "test";
        String salt = PasswordHasher.generateSalt();
        String hashedPassword = "18cca6183e11dc2a07bf5f63509f7beadd23b99ec727dc1b4290072315484210fbca55e07b6cc524fdc37f6558f284fee43a9e6f5cb851b4062e633faff2db09";
        assert !hashedPassword.equals(PasswordHasher.hashPassword(password, salt));
    }
}