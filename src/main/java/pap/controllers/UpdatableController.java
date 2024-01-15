package pap.controllers;

public interface UpdatableController {
    /**
     * An interface that is implemented by every controller class that needs to be updated.
     * This method allows for pages to update their values every time they are displayed.
     */
    void update();
}
