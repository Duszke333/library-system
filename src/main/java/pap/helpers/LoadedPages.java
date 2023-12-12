package pap.helpers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import pap.Pap;

import java.io.IOException;

public class LoadedPages {
    private LoadedPages() {}

    public static final Parent employeeManagePage;
    public static final Parent userManagePage;
    public static final Parent cataloguePage;
    public static final Parent userCreateAccountPage;
    public static final Parent loginPage;
    
    static {
        try {
            loginPage = new FXMLLoader(Pap.class.getResource("view/login-screen.fxml")).load();
            employeeManagePage = new FXMLLoader(Pap.class.getResource("view/employee-account-create.fxml")).load();
            userManagePage = new FXMLLoader(Pap.class.getResource("view/user-account-manage.fxml")).load();
            cataloguePage = new FXMLLoader(Pap.class.getResource("view/browse-catalog.fxml")).load();
            userCreateAccountPage = new FXMLLoader(Pap.class.getResource("view/user-account-create.fxml")).load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
