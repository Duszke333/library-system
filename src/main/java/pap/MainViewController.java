package pap;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class MainViewController {
    @FXML
    private Button buttonBack;
    @FXML
    private Button buttonAccounts;
    @FXML
    private Button buttonCatalogue;
    @FXML
    private MenuItem menuGotoRepo;
    @FXML
    private MenuItem menuQuit;
    @FXML
    private Text textMainView;
    @FXML
    private Pane contentPane;
    @FXML
    private ScrollPane scrollPane;
    
    private static final String REPO_URL = "https://gitlab-stud.elka.pw.edu.pl/papuga/pap2023z-z17";
    
    private static final Parent accountCreationPage;
    static {
        try {
            accountCreationPage = new FXMLLoader(MainViewController.class.getResource("create-account.fxml")).load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static final Parent cataloguepage;
    static {
        try {
            cataloguepage = new FXMLLoader(MainViewController.class.getResource("browse-catalog.fxml")).load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void initialize() throws IOException {
        var welcomeMsg = Files.readString(Path.of("README.md"));
        textMainView.setText(welcomeMsg);
        textMainView.setFont(Font.font(20));
        scrollPane.widthProperty().addListener(o -> {
            textMainView.setWrappingWidth(scrollPane.getWidth());
        });
        
        buttonBack.setOnAction(e -> contentPane.getChildren().setAll(scrollPane));
        buttonAccounts.setOnAction(e -> contentPane.getChildren().setAll(accountCreationPage));
        buttonCatalogue.setOnAction(e -> contentPane.getChildren().setAll(cataloguepage));
        menuGotoRepo.setOnAction(e -> {
            try {
                Desktop.getDesktop().browse(new URL(REPO_URL).toURI());
            } catch (IOException | URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        });
        menuQuit.setOnAction(e -> Platform.exit());
    }
}
