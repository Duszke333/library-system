package pap.controllers;

import javafx.scene.Parent;
import lombok.Getter;
import lombok.Setter;

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
    public static void switchVisibleContent(UpdatableController receiver, Parent... elements) {
        if (parent == null) {
            throw new NullPointerException("MainView cannot be null");
        }
        
        parent.contentPane.getChildren().setAll(elements);
        receiver.update();
    }
}
