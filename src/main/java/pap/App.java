package pap;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button("Dzien dobry");
        btn.setOnAction(actionEvent -> System.out.println("Dzien dobry"));

        StackPane root = new StackPane();
        root.getChildren().add(btn);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Papuga");
        primaryStage.show();
    }

    public static void main (String[] args) {
        launch(args);
    }
}
