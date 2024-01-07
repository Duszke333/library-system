package pap.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import lombok.Getter;
import pap.helpers.LoadedPages;
import pap.helpers.Login;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class MainViewController {
    @FXML
    private Text textMainView;
    @Getter
    @FXML
    public Pane contentPane;
    @FXML
    private ScrollPane scrollPane;
    private static final String REPO_URL = "https://gitlab-stud.elka.pw.edu.pl/papuga/pap2023z-z17";

    public void initialize() throws IOException {
        GlobalController.setParent(this);
        
        var welcomeMsg = Files.readString(Path.of("README.md"));
        textMainView.setText(welcomeMsg);
        textMainView.setFont(Font.font(22));
        scrollPane.widthProperty().addListener(o -> textMainView.setWrappingWidth(scrollPane.getWidth()));
    }
    
    @FXML
    private void buttonLoginPagePressed() {
        if (Login.getUserLoggedIn().isPresent()) {
            GlobalController.switchVisibleContent(LoadedPages.userDashboard);
            return;
        } 
        if (Login.getEmployeeLoggedIn().isPresent()) {
            GlobalController.switchVisibleContent(LoadedPages.employeeDashboard);
            return;
        }
        
        GlobalController.switchVisibleContent(LoadedPages.loginScreen);
    }
    
    @FXML
    private void buttonBackPressed() {
        contentPane.getChildren().setAll(scrollPane);
    }
    
    @FXML
    private void buttonCataloguePressed() {
        GlobalController.switchVisibleContent(LoadedPages.browseCatalog);
    }
    
    @FXML
    private void buttonBranchesPressed() {
        GlobalController.switchVisibleContent(LoadedPages.browseBranches);
    }
    
    @FXML
    private void menuGotoRepoPressed() {
        try {
            Desktop.getDesktop().browse(new URL(REPO_URL).toURI());
        } catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @FXML
    private void menuQuitPressed() {
        Platform.exit();
    }
}
