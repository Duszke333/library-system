package pap;

import atlantafx.base.theme.PrimerDark;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import pap.db.SessionFactoryMaker;

import java.io.IOException;

public class Pap extends Application {
    private static final int MIN_WIDTH = 1080;
    private static final int MIN_HEIGHT = 820;
    @Getter
    private static Stage stage;
    @Override
    public void start(Stage stage) throws IOException {
        new Thread(SessionFactoryMaker::getSessionFactory).start();
        var fxmlLoader = new FXMLLoader(getClass().getResource("view/main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), MIN_WIDTH, MIN_HEIGHT);
        Pap.stage = stage;
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
