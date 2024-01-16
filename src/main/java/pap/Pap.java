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
    /**
     * A main class of the application. It runs every process that needs to be run before the application starts.
     */

    // The minimum width and height of the application window.
    private static final int MIN_WIDTH = 1000;
    private static final int MIN_HEIGHT = 820;

    // The stage of the application.
    @Getter
    private static Stage stage;
    
    @Override
    public void start(Stage stage) throws IOException {
        /*
            A method that runs the application.
         */

        // create a new stage and set its properties
        Pap.stage = stage;
        stage.setMinHeight(MIN_HEIGHT);
        stage.setMinWidth(MIN_WIDTH);
        stage.setTitle("Hello!");

        // establish connection with the database
        new Thread(SessionFactoryMaker::getSessionFactory).start();

        // read system parameters from the parameters.properties file
        new Thread(pap.helpers.Parameters::readParameters).start();

        // check if there are any late returns and charge the users if needed
        new Thread(pap.helpers.PenaltyManager::checkLateReturns).start();

        // load the main-view page
        var fxmlLoader = new FXMLLoader(getClass().getResource("view/main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), MIN_WIDTH, MIN_HEIGHT);
        stage.setScene(scene);
        Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
