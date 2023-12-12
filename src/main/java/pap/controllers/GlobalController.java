package pap.controllers;

import javafx.scene.Parent;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

public class GlobalController {
    private GlobalController() {}
    
    @Getter
    @Setter
    private static MainViewController parent;

    /**
     * Add elements to main view contentPane, refreshes the caller and assumes the
     * elements are prepped and empty.
     * @param elements of type javafx.scene.Node
     */
    public static void switchVisibleContent(UpdatableController caller, Parent... elements) {
        if (parent == null) {
            throw new NullPointerException("MainView cannot be null");
        }
        
        caller.update();
        parent.contentPane.getChildren().setAll(elements);
    }
}
