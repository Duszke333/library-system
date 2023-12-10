
package pap;
import atlantafx.base.theme.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Pap extends Application {
    @Override
    public void start(Stage stage) throws IOException {
//        var fxmlLoader = new FXMLLoader(getClass().getResource("main-view.fxml"));
        var fxmlLoader = new FXMLLoader(getClass().getResource("user-login-screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 920, 640);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
