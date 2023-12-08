package pap;

import javafx.scene.control.Label;
import javafx.fxml.FXML;

public class MainView {
    @FXML
    private Label test;

    @FXML
    protected void OnButtonClicked() {test.setText("TESTOWANE FEST JEST");}
}
