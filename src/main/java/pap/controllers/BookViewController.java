package pap.controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Pair;
import pap.db.Entities.*;
import javafx.fxml.FXML;
import lombok.Setter;

import static pap.db.Entities.Book.*;


import javafx.scene.input.MouseEvent;
import pap.db.Repository.BookRepository;
import pap.db.Repository.RentalRepository;
import pap.db.Repository.WishRepository;
import pap.db.Repository.*;
import pap.helpers.LoadedPages;
import pap.helpers.Login;
import pap.helpers.Parameters;
import pap.helpers.RentalRecord;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

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
    Label actionLabel;
    @FXML
    Label wishLabel;
    @FXML
    Button returnButton;
    @FXML
    Text returnText;

    @Setter
    static Book book = new Book(0, "isbn", "title", "author", "genre", 0, "language", 0, "publisher", BookStatus.Available, "description", new java.sql.Date(System.currentTimeMillis()), "cover");

    @FXML
    Button actionButton;

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

    @FXML
    protected void extendPressed() {
        System.out.println("Extend pressed");
    }

    @FXML
    protected void reservePressed() {
        System.out.println("Reserve pressed");
    }

    @FXML
    protected void returnPressed() {
        System.out.println("Return pressed");
    }

    @FXML
    protected void resignPressed() {
        System.out.println("Resign pressed");
    }

    @FXML
    protected void orderPressed() {
        BookRental rental = new BookRental();
        rental.setBookId(book.getBookId());
        rental.setUserId(Login.getUserLoggedIn().get());
        var date = new java.sql.Date(System.currentTimeMillis());
        rental.setDateRented(date);
        date.setMonth(date.getMonth() + 1);
        rental.setDateToReturn(date);
        rental.setWasProlonged(false);
        new RentalRepository().create(rental);
        book.setStatus(BookStatus.Rented);
        new BookRepository().update(book);
        actionButton.setDisable(true);
        actionLabel.setText("You have successfully ordered this book. It must be returned by " + date);
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

    private void updateAction() {
        returnButton.setVisible(false);
        returnText.setVisible(false);

        String status = book.getStatus();
        if (status.equals(BookStatus.Unavailable)) {        // G
            actionButton.setText("Order");
            actionButton.setDisable(true);
            actionLabel.setText("This book is unavailable.");
            return;
        }

        int uid = Login.getUserLoggedIn().orElse(-1);
        if (uid == -1) {                                    // G
            actionButton.setDisable(true);
            actionLabel.setTextFill(javafx.scene.paint.Color.RED);
            actionLabel.setText("You must be logged in to do that!");
            return;
        }
        actionButton.setDisable(false);
        actionLabel.setTextFill(Color.WHITE);

        if (status.equals(BookStatus.Available)) {      // G
            actionButton.setText("Order");
            actionButton.setOnAction(event -> orderPressed());
            actionLabel.setText("Book is available and can be ordered.");
            return;
        }

        RentalRepository repo = new RentalRepository();
        // check if user rented this book
        BookRental rental = repo.getCurrentBookRental(book.getBookId());
        if (rental.getUserId() == uid) {                // G
            returnButton.setVisible(true);
            var returnDate = rental.getDateToReturn();
            returnText.setText("You are currently renting this book (must be returned on " + returnDate + ")");
            returnText.setVisible(true);

            // action button setting
            if (status.equals(BookStatus.Rented)) {
                actionButton.setText("Extend");
                actionButton.setOnAction(event -> extendPressed());     // G
                if (rental.isWasProlonged()) {
                    actionButton.setDisable(true);
                    actionLabel.setText("You have already extended your rental");   // G
                } else {
                    returnDate.setMonth(returnDate.getMonth() + 1);
                    actionLabel.setText("You can extend your rental by one month (until " + returnDate + ")");  // G
                }
            } else {
                actionButton.setDisable(true);
                actionLabel.setText("You cannot extend the rental as there are people in queue for this book"); // G
            }
            return;
        }

        List<RentingQueue> queue = repo.getRentingQueuesByBookId(book.getBookId());

        if (queue.size() == 0) {
            actionButton.setText("Reserve");    // G
            actionButton.setOnAction(event -> reservePressed());
            actionLabel.setText("This book is currently rented until" + rental.getDateToReturn() + ". You can reserve it and pick it up on the day after.");
            return;
        }

        // check if user is in queue
        for (RentingQueue userEntry : queue) {
            if (userEntry.getUserId() == uid) {             // G
                // TODO: check if he can rent the book
                var pickupDate = userEntry.getDateToRent();
                actionButton.setText("Leave the queue");
                actionButton.setOnAction(event -> resignPressed());
                actionLabel.setText("You are currently in queue for this book (should be available on " + pickupDate + ").");
                return;
            }
        }

        actionButton.setText("Join the queue");
        actionButton.setOnAction(event -> reservePressed());
        if (queue.size() < Parameters.getMaxQueueLength()) {
            var lastDate = queue.get(queue.size() - 1).getDateToReturn();
            actionLabel.setText("This book is reserved until " + lastDate + ". You can join the queue and pick it up on the day after.");
        } else {
            actionButton.setDisable(true);
            var lastDate = queue.get(queue.size() - 1).getDateToReturn();
            actionLabel.setText("This book is reserved until " + lastDate + ", but the queue is full so you cannot join it.");
        }
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
            GlobalController.switchVisibleContent(LoadedPages.reportBookViewController, LoadedPages.reportBookView);
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
        updateAction();
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
