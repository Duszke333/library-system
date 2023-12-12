
package pap;
import atlantafx.base.theme.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Pap extends Application {
    private static final int MIN_WIDTH = 1080;
    private static final int MIN_HEIGHT = 820;
    
    @Override
    public void start(Stage stage) throws IOException {
        var fxmlLoader = new FXMLLoader(getClass().getResource("view/main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), MIN_WIDTH, MIN_HEIGHT);
        stage.setMinHeight(MIN_HEIGHT);
        stage.setMinWidth(MIN_WIDTH);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
