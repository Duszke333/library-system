package pap.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import lombok.Setter;
import pap.db.Entities.Address;
import pap.db.Entities.Branch;
import pap.db.Repository.AddressRepository;
import pap.db.Repository.BranchRepository;
import pap.helpers.ConstraintChecker;
import pap.helpers.LoadedPages;

import java.net.URL;
import java.util.ResourceBundle;


public class BranchManagerController implements UpdatableController, Initializable {
    @Setter
    private static Branch branch;
    private static Address addr;
    @FXML
    private Text workingMode;
    @FXML
    private TextField nameInput;
    @FXML
    private TextField countryInput;
    @FXML
    private TextField cityInput;
    @FXML
    private TextField streetInput;
    @FXML
    private TextField postalCodeInput;
    @FXML
    private TextField houseNumberInput;
    @FXML
    private TextField flatNumberInput;
    @FXML
    private Text operationStatus;
    @FXML
    private Button perform;

    @Setter
    private static Boolean creationMode;

    @FXML
    private void create() {
        operationStatus.setFill(javafx.scene.paint.Color.RED);

        String name = nameInput.getText();
        String country = countryInput.getText();
        String city = cityInput.getText();
        String street = streetInput.getText();
        String postalCode = postalCodeInput.getText();
        String houseNumber = houseNumberInput.getText();
        String flatNumber = flatNumberInput.getText();

        if (name.isEmpty() || country.isEmpty() || city.isEmpty() || street.isEmpty() || postalCode.isEmpty() || houseNumber.isEmpty()) {
            operationStatus.setText("All fields must be filled!");
            operationStatus.setVisible(true);
            return;
        }

        if (name.length() > 128) {
            operationStatus.setText("Name too long!");
            operationStatus.setVisible(true);
            return;
        }

        Address addr = new Address();
        addr.setCountry(country);
        addr.setCity(city);
        addr.setStreet(street);
        addr.setPostalCode(postalCode);
        addr.setHouseNumber(houseNumber);
        addr.setFlatNumber(flatNumber);

        int error = ConstraintChecker.checkAddress(addr);
        if (error != -1) {
            operationStatus.setText("Error: " + ConstraintChecker.AddressErrors.values()[error].toString());
            operationStatus.setVisible(true);
            return;
        }
        new AddressRepository().create(addr);

        Branch branch = new Branch();
        branch.setName(name);
        branch.setAddressId(addr.getAddressId());
        new BranchRepository().create(branch);

        operationStatus.setFill(javafx.scene.paint.Color.GREEN);
        operationStatus.setText("Branch created successfully!");
        operationStatus.setVisible(true);
    }

    @FXML
    protected void updateBranch() {
        operationStatus.setFill(javafx.scene.paint.Color.RED);
        String name = nameInput.getText();
        String country = countryInput.getText();
        String city = cityInput.getText();
        String street = streetInput.getText();
        String postalCode = postalCodeInput.getText();
        String houseNumber = houseNumberInput.getText();
        String flatNumber = flatNumberInput.getText();

        if (name.isEmpty() || country.isEmpty() || city.isEmpty() || street.isEmpty() || postalCode.isEmpty() || houseNumber.isEmpty()) {
            operationStatus.setText("All fields must be filled!");
            operationStatus.setVisible(true);
            return;
        }

        if (name.length() > 128) {
            operationStatus.setText("Name too long!");
            operationStatus.setVisible(true);
            return;
        }

        addr.setCountry(country);
        addr.setCity(city);
        addr.setStreet(street);
        addr.setPostalCode(postalCode);
        addr.setHouseNumber(houseNumber);
        addr.setFlatNumber(flatNumber);

        int error = ConstraintChecker.checkAddress(addr);
        if (error != -1) {
            operationStatus.setText("Error: " + ConstraintChecker.AddressErrors.values()[error].toString());
            operationStatus.setVisible(true);
            return;
        }

        new AddressRepository().update(addr);

        branch.setName(name);
        new BranchRepository().update(branch);

        operationStatus.setText("Branch updated successfully!");
        operationStatus.setFill(javafx.scene.paint.Color.GREEN);
        operationStatus.setVisible(true);
    }

    @FXML
    protected void goBack() {
        GlobalController.switchVisibleContent(LoadedPages.employeeManageBranches);
    }

    @Override
    public void update() {
        if (!creationMode) {
            workingMode.setText("Update branch information");
            perform.setText("Update information");
            perform.setOnAction(event -> updateBranch());
            nameInput.setText(branch.getName());
            addr = new AddressRepository().getById(branch.getAddressId());
            countryInput.setText(addr.getCountry());
            cityInput.setText(addr.getCity());
            streetInput.setText(addr.getStreet());
            postalCodeInput.setText(addr.getPostalCode());
            houseNumberInput.setText(addr.getHouseNumber());
            flatNumberInput.setText(addr.getFlatNumber());
        } else {
            workingMode.setText("Create a new branch");
            perform.setText("Create branch");
            perform.setOnAction(event -> create());
            nameInput.clear();
            countryInput.clear();
            cityInput.clear();
            streetInput.clear();
            postalCodeInput.clear();
            houseNumberInput.clear();
            flatNumberInput.clear();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCreationMode(true);
        update();
    }
}
