package pap.controllers;

import javafx.scene.control.TextArea;
import pap.db.Entities.Book;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import lombok.Setter;



import javafx.scene.input.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BookViewController {
    @FXML
    Label titleLabel;
    @FXML
    Label authorLabel;
    @FXML
    Label genreLabel;
    @FXML
    Label publicationYearLabel;
    @FXML
    Label languageLabel;
    @FXML
    Label pageCountLabel;
    @FXML
    Label publisherLabel;
    @FXML
    TextArea descriptionLabel;
    @FXML
    Label isAvailableLabel;

    @FXML
    Label dateAddedLabel;

    @FXML
    Label orderLabel;

    @Setter
    Book book;

    @FXML
    Button orderButton;



    public void displayTitle(String title) {
        titleLabel.setText("Tytuł: " + title);
    }

    public void displayAuthor(String author) {
        authorLabel.setText("Autor: " + author);
    }

    public void displayGenre(String genre) {
        genreLabel.setText("Gatunek: " + genre);
    }

    public void displayPublicationYear(int publicationYear) {
        publicationYearLabel.setText("Rok publikacji: " + publicationYear);
    }

    public void displayLanguage(String language) {
        languageLabel.setText("Język: " + language);
    }

    public void displayPageCount(int pageCount) {
        pageCountLabel.setText("Liczba stron: " + pageCount);
    }

    public void displayPublisher(String publisher) {
        publisherLabel.setText("Wydawca: " + publisher);
    }

    public void displayDescription(String description) {
        descriptionLabel.setText("Opis: " + description);
    }

    public void displayAvailability(boolean isAvailable) {
        isAvailableLabel.setText("Dostępność: " + (isAvailable ? "Dostępna" : "Niedostępna"));
    }

    public void displayDateAdded(Date dateAdded) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        dateAddedLabel.setText("Data dodania: " + dateFormat.format(dateAdded));
    }


    public void orderButtonClicked(MouseEvent mouseEvent){
        if(book.isAvailable()){
            //#TODO
            //#zamawianie ksiazki
            orderButton.setStyle("-fx-background-color: #00ff00;");
            orderLabel.setText("Ksiazka wypozyczona");
        }else {
            orderButton.setStyle("-fx-background-color: #ffff00;");
            orderLabel.setText("Ksiazka aktualnie jest wypozyczona. Jestes x w kolejce");
        }

    }
}
