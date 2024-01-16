package pap.controllers;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import pap.helpers.LoadedPages;

/**
 * Controller overseeing current visible content. Called GlobalController as it is
 * available <strong>globally</strong> regardless of the caller. <strong>MainViewController parent</strong>
 * must be set before usage.
 */
public class GlobalController {
    private GlobalController() {}
    
    @Getter
    @Setter
    private static MainViewController parent;

    /**
     * Add elements to main view contentPane, refreshes the caller. This method 
     * and assumes and requires <strong>@NonNull</strong> content
     * @param page page to be loaded and refreshed
     * @throws NullPointerException if parent is <strong>null</strong>
     */
    public static void switchVisibleContent(@NonNull LoadedPages.Page page) throws NullPointerException {
        if (parent == null) {
            throw new NullPointerException("MainView cannot be null");
        }
        
        parent.contentPane.getChildren().setAll(page.node());
        page.controller().update();
    }
}
