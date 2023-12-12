package pap.controllers;

import javafx.scene.Node;
import lombok.Getter;
import lombok.Setter;

public class GlobalController {
    private GlobalController() {}
    
    @Getter
    @Setter
    private static MainViewController parent;

    /**
     * Add elements to main view contentPane
     * @param elements of type javafx.scene.Node
     */
    public static void setContentPane(Node... elements) {
        if (parent == null) {
            throw new RuntimeException("Cannot set contentPane of null Object");
        }
//        elements.update();    TODO: RESLOVE THIS
        parent.contentPane.getChildren().setAll(elements);
    }
}
