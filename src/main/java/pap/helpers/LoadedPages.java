package pap.helpers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import pap.Pap;

import java.io.IOException;

public class LoadedPages {
    private LoadedPages() {}

    public static final Parent userLoginPage;
    public static final Parent cataloguepage;
    public static final Parent createAccountPage;
    
    static {
        try {
            userLoginPage = new FXMLLoader(Pap.class.getResource("view/user-login-screen.fxml")).load();
            cataloguepage = new FXMLLoader(Pap.class.getResource("view/browse-catalog.fxml")).load();
            createAccountPage = new FXMLLoader(Pap.class.getResource("view/user-account-create.fxml")).load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
