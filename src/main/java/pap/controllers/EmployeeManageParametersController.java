package pap.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

import static pap.helpers.Parameters.*;

public class EmployeeManageParametersController implements UpdatableController, Initializable {
    /**
     * A controller class for employee-manage-parameters page which allows an employee
     * to change values of the system parameters.
     */
    @FXML
    private TextField maxQueueLengthInput;
    @FXML
    private TextField dailyPenaltyInput;
    @FXML
    private TextField bookDamagePenaltyInput;
    @FXML
    private TextField bookLossPenaltyInput;
    @FXML
    private Text updateStatus;

    /**
     * A method that updates the system parameters.
     */
    @FXML
    protected void updateParameters() {
        updateStatus.setFill(javafx.scene.paint.Color.RED);
        updateStatus.setVisible(false);

        // Get the input data
        String maxQueueLength = maxQueueLengthInput.getText();
        String dailyPenalty = dailyPenaltyInput.getText();
        String bookDamagePenalty = bookDamagePenaltyInput.getText();
        String bookLossPenalty = bookLossPenaltyInput.getText();

        // Check if all the fields are filled
        if (maxQueueLength.isEmpty() || dailyPenalty.isEmpty() || bookDamagePenalty.isEmpty() || bookLossPenalty.isEmpty()) {
            updateStatus.setText("All fields must be filled");
            updateStatus.setVisible(true);
            return;
        }

        // Check if all the fields are numbers
        int maxQueueLengthInt;
        double dailyPenaltyDouble;
        double bookDamagePenaltyDouble;
        double bookLossPenaltyDouble;

        try {
            maxQueueLengthInt = Integer.parseInt(maxQueueLength);
            dailyPenaltyDouble = Double.parseDouble(dailyPenalty);
            bookDamagePenaltyDouble = Double.parseDouble(bookDamagePenalty);
            bookLossPenaltyDouble = Double.parseDouble(bookLossPenalty);
        } catch (NumberFormatException e) {
            updateStatus.setText("All inputs must be numbers");
            updateStatus.setVisible(true);
            return;
        }

        // Check if all the fields are positive
        if (maxQueueLengthInt <= 0 || dailyPenaltyDouble <= 0 || bookDamagePenaltyDouble <= 0 || bookLossPenaltyDouble <= 0) {
            updateStatus.setText("All values must be positive");
            updateStatus.setVisible(true);
            return;
        }

        // Check if the parameters have logical values
        if (bookDamagePenaltyDouble < dailyPenaltyDouble) {
            updateStatus.setText("Book damage penalty must be greater than daily penalty");
            updateStatus.setVisible(true);
            return;
        }

        // Check if the parameters have logical values
        if (bookLossPenaltyDouble < bookDamagePenaltyDouble) {
            updateStatus.setText("Book loss penalty must be greater than daily penalty");
            updateStatus.setVisible(true);
            return;
        }

        // Update the parameters
        setMaxQueueLength(maxQueueLengthInt);
        setDailyPenalty(dailyPenaltyDouble);
        setBookDamagePenalty(bookDamagePenaltyDouble);
        setBookLossPenalty(bookLossPenaltyDouble);
        writeParameters();

        // inform the employee about success
        updateStatus.setFill(javafx.scene.paint.Color.GREEN);
        updateStatus.setText("Parameters updated successfully!");
        updateStatus.setVisible(true);
    }

    @Override
    public void update() {
        maxQueueLengthInput.setText(pap.helpers.Parameters.getMaxQueueLength().toString());
        dailyPenaltyInput.setText(pap.helpers.Parameters.getDailyPenalty().toString());
        bookDamagePenaltyInput.setText(pap.helpers.Parameters.getBookDamagePenalty().toString());
        bookLossPenaltyInput.setText(pap.helpers.Parameters.getBookLossPenalty().toString());
        updateStatus.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       update();
    }
}
