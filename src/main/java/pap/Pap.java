
package pap;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Pap extends Application {
    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(Pap.class.getResource("create-account.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(Pap.class.getResource("login-screen.fxml"));
//        FXMLLoader fxmlLoader = new FXMLLoader(Pap.class.getResource("browse-catalog.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        //stage.setMaximized(true);   // subsceny; lepsze guziki; wybór kart?;
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
