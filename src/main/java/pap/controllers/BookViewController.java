package pap.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.util.Pair;
import pap.db.DAO.EntityDAO.UserDAO;
import pap.db.Entities.*;
import javafx.fxml.FXML;
import lombok.Setter;

import static pap.db.Entities.Book.*;


import javafx.scene.input.MouseEvent;
import pap.db.Repository.*;
import pap.helpers.LoadedPages;
import pap.helpers.Login;
import pap.helpers.RentalRecord;

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

    @FXML
    Button reportButton;

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
        wishButton.setText("Add a book to the wishlist");
        if (uid == -1){
            return;
        }
        if(!(new WishRepository().getWishListByUserAndBook(uid, book.getBookId()) == null)){
            wishButton.setText("Remove a book from the wishlist");
        }
    }

    public void showReportButton(){
        int uid = Login.getUserLoggedIn().orElse(-1);
        if (uid == -1 || new RentalRepository().isRentedByUser(uid, book.getBookId())){
            reportButton.setVisible(false);
            return;
        }
        reportButton.setVisible(true);
    }

    public void displayDateAdded(Date dateAdded) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        dateAddedLabel.setText("Data dodania: " + dateFormat.format(dateAdded));
    }


    public void orderButtonClicked(MouseEvent mouseEvent){
        gradeText.setText("Your grade: "+ gradeSlider.getValue());
        int logged;
        try {
            logged = Login.getUserLoggedIn().get();
        } catch (Exception e) {
            orderLabel.setText("You need to be logged in to borrow a book");
            return;
        }
        if(book.getStatus().equals(Book.BookStatus.Available)){
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
        orderLabel.setText("");
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
            wishButton.setText("Add a book to the wishlist");
            wishLabel.setText("Login to add a book to wishlist");
            return;
        }
        BookWishList wish = new BookWishList();
        WishRepository wishRepo = new WishRepository();
        BookWishList wishListByUserAndBook = wishRepo.getWishListByUserAndBook(uid, book.getBookId());
        if (wishListByUserAndBook == null) {
            wish.setBookId(book.getBookId());
            wish.setUserId(uid);
            wish.setDateAdded(new java.sql.Date(System.currentTimeMillis()));
            new WishRepository().create(wish);
            wishButton.setText("Remove a book from the wishlist");
            wishLabel.setText("Book added to wishlist");
        }else{
            new WishRepository().delete(wishListByUserAndBook);
            wishLabel.setText("Book removed from wishlist");
            wishButton.setText("Add a book to the wishlist");
        }
    }

    public void reportButtonClicked(MouseEvent mouseEvent){
        BookReport existingReport = new ReportRepository().getReportByUserAndBook(Login.getUserLoggedIn().get(), book.getBookId());
        if (existingReport == null) {
            BookReportController.setBook(book);
            BookReportController.setUserId(Login.getUserLoggedIn().get());
            GlobalController.switchVisibleContent(LoadedPages.bookReport);
        } else {
            Alert alreadyReportedAlert = new Alert(
                    Alert.AlertType.WARNING,
                    "You have already reported this book. You report is pending.",
                    ButtonType.OK
            );
            alreadyReportedAlert.setHeaderText("Already Reported");
            alreadyReportedAlert.showAndWait();
        }

    }

    @Override
    public void update() {
        updateDisplay();
        updateGrading();
        displayWishStatus();
        showReportButton();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gradeSlider.valueProperty().addListener((observableValue, number, t1) -> gradeText.setText("Your grade: "+ Math.round(gradeSlider.getValue() * 2) / 2.0));
        update();
    }
}
