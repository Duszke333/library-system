package pap.integration;

import javafx.scene.control.Label;
import org.junit.Test;

public class EmployeeLoginTest extends GenericIntegrationTest {
    @Override
    public void setUp() throws Exception {
        resourcePath = "resources/employee-login-screen.fxml";
        super.setUp();
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testLogin() {
        Label label = lookup("#loginUsername").query();

        clickOn("#loginUsername");
        write("admin");
        assert (label.getText().equals("admin"));
    }
}
