package pap;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.Scene;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button("Dzien dobry");
        btn.setOnAction(actionEvent -> System.out.println("Dzien dobry"));
        btn.setLayoutX(225);
        btn.setLayoutY(250);
        btn.setPrefWidth(150);

        Group group = new Group();
        Circle face = new Circle();
        face.setCenterX(300);
        face.setCenterY(200);
        face.setRadius(150);
        face.setFill(Color.YELLOW);

        Circle left_eye = new Circle();
        set_eye(left_eye, 225, 150, 50, Color.WHITE);
        Circle left_pupil = new Circle();
        set_eye(left_pupil, 200, 165, 25, Color.BLACK);

        Circle right_eye = new Circle();
        set_eye(right_eye, 375, 150, 50, Color.WHITE);
        Circle right_pupil = new Circle();
        set_eye(right_pupil, 400, 135, 25, Color.BLACK);

        group.getChildren().setAll(face, left_eye, right_eye, left_pupil, right_pupil, btn);

        Scene scene = new Scene(group, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Papuga");
        primaryStage.show();
    }

    private void set_eye(Circle eye, double x, double y, double radius, Color color) {
        eye.setCenterX(x);
        eye.setCenterY(y);
        eye.setRadius(radius);
        eye.setFill(color);
    }

    public static void main (String[] args) {
        launch(args);
    }
}
