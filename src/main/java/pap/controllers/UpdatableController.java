package pap.controllers;

/**
 * Interface that is implemented by every controller class that needs to be updated.
 * This method allows for pages to update their values every time they are displayed.
 */
public interface UpdatableController {
    void update();
}
