package pap.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

import static pap.helpers.Parameters.*;

public class EmployeeManageParametersController implements UpdatableController, Initializable {
    @FXML
    private TextField maxQueueLengthInput;
    @FXML
    private TextField dailyPenaltyInput;
    @FXML
    private TextField bookDamagePenaltyInput;
    @FXML
    private Text updateStatus;

    @FXML
    protected void updateParameters() {
        updateStatus.setFill(javafx.scene.paint.Color.RED);
        updateStatus.setVisible(false);
        String maxQueueLength = maxQueueLengthInput.getText();
        String dailyPenalty = dailyPenaltyInput.getText();
        String bookDamagePenalty = bookDamagePenaltyInput.getText();

        if (maxQueueLength.isEmpty() || dailyPenalty.isEmpty() || bookDamagePenalty.isEmpty()) {
            updateStatus.setText("All fields must be filled");
            updateStatus.setVisible(true);
            return;
        }

        int maxQueueLengthInt;
        double dailyPenaltyDouble;
        double bookDamagePenaltyDouble;

        try {
            maxQueueLengthInt = Integer.parseInt(maxQueueLength);
            dailyPenaltyDouble = Double.parseDouble(dailyPenalty);
            bookDamagePenaltyDouble = Double.parseDouble(bookDamagePenalty);
        } catch (NumberFormatException e) {
            updateStatus.setText("All inputs must be numbers");
            updateStatus.setVisible(true);
            return;
        }

        if (maxQueueLengthInt <= 0 || dailyPenaltyDouble <= 0 || bookDamagePenaltyDouble <= 0) {
            updateStatus.setText("All values must be positive");
            updateStatus.setVisible(true);
            return;
        }

        if (bookDamagePenaltyDouble < dailyPenaltyDouble) {
            updateStatus.setText("Book damage penalty must be greater than daily penalty");
            updateStatus.setVisible(true);
            return;
        }

        setMaxQueueLength(maxQueueLengthInt);
        setDailyPenalty(dailyPenaltyDouble);
        setBookDamagePenalty(bookDamagePenaltyDouble);
        writeParameters();
        updateStatus.setFill(javafx.scene.paint.Color.GREEN);
        updateStatus.setText("Parameters updated successfully!");
        updateStatus.setVisible(true);
    }

    @Override
    public void update() {
        maxQueueLengthInput.setText(pap.helpers.Parameters.getMaxQueueLength().toString());
        dailyPenaltyInput.setText(pap.helpers.Parameters.getDailyPenalty().toString());
        bookDamagePenaltyInput.setText(pap.helpers.Parameters.getBookDamagePenalty().toString());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       update();
    }
}
