package pap.controllers;

import lombok.Getter;
import lombok.Setter;
import pap.helpers.LoadedPages;

public class GlobalController {
    private GlobalController() {}
    
    @Getter
    @Setter
    private static MainViewController parent;

    /**
     * Add elements to main view contentPane, refreshes the caller and assumes the
     * elements are prepped and empty.
     * @param page page to be loaded and refreshed
     */
    public static void switchVisibleContent(LoadedPages.Page page) {
        if (parent == null) {
            throw new NullPointerException("MainView cannot be null");
        }
        
        parent.contentPane.getChildren().setAll(page.node());
        page.controller().update();
    }
}
