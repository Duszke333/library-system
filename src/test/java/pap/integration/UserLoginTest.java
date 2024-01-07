package pap.integration;

import javafx.scene.control.Label;
import org.junit.Test;

public class UserLoginTest extends GenericIntegrationTest {
    @Override
    public void setUp() throws Exception {
        resourcePath = "resources/user-login-screen.fxml";
        super.setUp();
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testLogin() {
        Label label = lookup("#loginEmail").query();

        clickOn("#loginEmail");
        write("user@user.user");
        assert(label.getText().equals("user@user.user"));
        clickOn("#loginPassword");
        write("user");
        clickOn("#loginButton");
    }
}