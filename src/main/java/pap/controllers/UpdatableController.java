package pap.controllers;

/**
 * Interface that is implemented by every controller class that needs to be updated.
 * This method allows for pages to update their values every time they are displayed.
 */
public interface UpdatableController {
    /**
     * This method updates the controller. Implementation is completely dependent
     * on the implementing controller, so there is no provided default. <br><br>
     * 
     * Good design would be to implement action that <b>makes sense </b>
     * to be called frequently and whenever there is a <b>need</b> to
     * update visible or non-visible content. Something akin to setting the labels
     * to <i>empty</i> upon visiting the page is recommended.
     */
    void update();
}
