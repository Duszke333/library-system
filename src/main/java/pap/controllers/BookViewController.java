package pap.controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.util.Pair;
import pap.db.Entities.Book;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import lombok.Setter;

import static pap.db.Entities.Book.*;


import javafx.scene.input.MouseEvent;
import pap.db.Entities.BookGrade;
import pap.db.Entities.BookRental;
import pap.db.Entities.BookWishList;
import pap.db.Repository.BookRepository;
import pap.db.Repository.RentalRepository;
import pap.db.Repository.WishRepository;
import pap.helpers.Login;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.List;

public class BookViewController implements UpdatableController, Initializable {
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
    Label grade;

    @FXML
    Label orderLabel;

    @FXML
    Label wishLabel;

    @Setter
    static Book book = new Book(0, "isbn", "title", "author", "genre", 0, "language", 0, "publisher", BookStatus.Available, "description", new java.sql.Date(System.currentTimeMillis()), "cover");

    @FXML
    Button orderButton;

    @FXML
    Text gradeText;

    @FXML
    Slider gradeSlider;
    @FXML
    Button wishButton;

    @FXML
    Button gradeButton;

    @FXML
    Text gradeStatus;

    @Deprecated
    public void displayTitle(String title) {
        titleLabel.setText("Tytuł: " + title);
    }

    @Deprecated
    public void displayAuthor(String author) {
        authorLabel.setText("Autor: " + author);
    }

    @Deprecated
    public void displayGenre(String genre) {
        genreLabel.setText("Gatunek: " + genre);
    }

    @Deprecated
    public void displayPublicationYear(int publicationYear) {
        publicationYearLabel.setText("Rok publikacji: " + publicationYear);
    }

    @Deprecated
    public void displayLanguage(String language) {
        languageLabel.setText("Język: " + language);
    }

    @Deprecated
    public void displayPageCount(int pageCount) {
        pageCountLabel.setText("Liczba stron: " + pageCount);
    }

    @Deprecated
    public void displayPublisher(String publisher) {
        publisherLabel.setText("Wydawca: " + publisher);
    }

    @Deprecated
    public void displayDescription(String description) {
        descriptionLabel.setText("Opis: " + description);
    }

    @Deprecated
    public void displayAvailability(String status) {
        isAvailableLabel.setText("Dostępność: " + status);
    }

    public void displayWishStatus() {
        int uid = Login.getUserLoggedIn().orElse(-1);
        wishLabel.setText("");
        wishButton.setText("Add book to wish list");
        if (uid == -1){
            return;
        }
        if(!(new WishRepository().getWishListByUserAndBook(uid, book.getBookId()).isEmpty())){
            wishButton.setText("Remove from wish list");
        }
    }

    public void displayDateAdded(Date dateAdded) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        dateAddedLabel.setText("Data dodania: " + dateFormat.format(dateAdded));
    }


    public void orderButtonClicked(MouseEvent mouseEvent){
        gradeText.setText("Your grade: "+ gradeSlider.getValue());
        if(book.getStatus().equals(Book.BookStatus.Available)){
            int logged;
            try {
                logged = Login.getUserLoggedIn().get();
            } catch (Exception e) {
                orderLabel.setText("Musisz być zalogowany, aby wypożyczyć książkę");
                return;
            }
            book.setStatus(BookStatus.Unavailable);
            new BookRepository().update(book);
            BookRental rent = new BookRental();
            rent.setBookId(book.getBookId());
            rent.setUserId(logged);
            rent.setDateRented(new java.sql.Date(System.currentTimeMillis()));
            var returnDate = new java.sql.Date(System.currentTimeMillis());
            returnDate.setMonth(returnDate.getMonth() + 1);
            rent.setDateToReturn(returnDate);
            new RentalRepository().create(rent);
            orderLabel.setText("Book rented successfully!");
        }else {
//            orderButton.setStyle("-fx-background-color: #ffff00;");
            orderLabel.setText("Ksiazka aktualnie jest wypozyczona. Jestes x w kolejce");
        }
    }

    @FXML
    protected void gradeButtonPressed() {
        int uid = Login.getUserLoggedIn().get();
        int bookId = book.getBookId();
        double gradeValue = gradeSlider.getValue();
        var grade = new BookGrade();
        grade.setBookId(bookId);
        grade.setUserId(uid);
        grade.setGrade(gradeValue);
        grade.setDateAdded(new java.sql.Date(System.currentTimeMillis()));
        new BookRepository().addBookGrade(grade);

        gradeButton.setDisable(true);
        gradeStatus.setText("You have already graded this book!");
        gradeSlider.setDisable(true);
    }

    private void updateDisplay() {
        titleLabel.setText("Title: " + book.getTitle());
        authorLabel.setText("Author: " + book.getAuthor());
        genreLabel.setText("Genre: " + book.getGenre());
        publicationYearLabel.setText("Publication year: " + book.getPublicationYear());
        languageLabel.setText("Language: " + book.getLanguage());
        pageCountLabel.setText("Page count: " + book.getPageCount());
        publisherLabel.setText("Publisher: " + book.getPublisher());
        descriptionLabel.setText("Description: " + book.getDescription());
        isAvailableLabel.setText("Availability: " + book.getStatus());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        dateAddedLabel.setText("Date added: " + dateFormat.format(book.getDateAdded()));
        Pair<Integer, Double> pair = new BookRepository().getBookGradeCountAndAverageGrade(book.getBookId());
        if (pair == null) {
            grade.setText("Grade: 0.0 (this book hasn't been graded yet)");
            return;
        }
        grade.setText("Grade: " + pair.getValue() + " (based on " + pair.getKey() + " grades)");
    }

    private void updateGrading() {
        int uid = Login.getUserLoggedIn().orElse(-1);
        if (uid == -1) {
            gradeButton.setDisable(true);
            gradeStatus.setText("You must be logged in to do that!");
            gradeSlider.setDisable(true);
            gradeSlider.setValue(1);
            gradeText.setText("Your grade: 1.0");
            return;
        }
        var grade = new BookRepository().getThisBookGradeByUser(book.getBookId(), uid);
        if (grade != null) {
            gradeButton.setDisable(true);
            gradeStatus.setText("You have already graded this book!");
            gradeSlider.setDisable(true);
            gradeSlider.setValue(grade.getGrade());
            gradeText.setText("Your grade: " + grade.getGrade());
            return;
        }

        gradeButton.setDisable(false);
        gradeStatus.setText("");
        gradeSlider.setDisable(false);
        gradeSlider.setValue(1);
        gradeText.setText("Your grade: 1.0");
    }

    public void wishButtonClicked(MouseEvent mouseEvent){
        int uid = Login.getUserLoggedIn().orElse(-1);
        if (uid == -1){
            wishButton.setText("Add book to wish list");
            wishLabel.setText("Musisz być zalogowany, aby dodać do listy życzeń");
            return;
        }
        BookWishList wish = new BookWishList();
        WishRepository wish_repo = new WishRepository();
        List<BookWishList> wishListByUserAndBook = wish_repo.getWishListByUserAndBook(uid, book.getBookId());
        if (wishListByUserAndBook.isEmpty()) {
            wish.setBookId(book.getBookId());
            wish.setUserId(uid);
            wish.setDateAdded(new java.sql.Date(System.currentTimeMillis()));
            new WishRepository().create(wish);
            wishButton.setText("Remove from wish list");
            wishLabel.setText("Book added to wish list successfully!");
        }else{
            BookWishList wish_to_delete = wishListByUserAndBook.get(0);
            new WishRepository().delete(wish_to_delete);
            wishLabel.setText("Book removed from wish list successfully");
            wishButton.setText("Add book to wish list");
        }
    }

    @Override
    public void update() {
        updateDisplay();
        updateGrading();
        displayWishStatus();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gradeSlider.valueProperty().addListener((observableValue, number, t1) -> gradeText.setText("Your grade: "+ Math.round(gradeSlider.getValue() * 2) / 2.0));
        update();
    }
}
