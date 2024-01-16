package pap.controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Pair;
import pap.Pap;
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

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

public class BookViewController implements UpdatableController, Initializable {
    /**
     * A controller class for book-view page which shows all the details about the selected book.
     */
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
    @FXML
    ImageView imageView;
    @FXML
    StackPane contentPane;

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

    /**
     * A method that displays the status of the book in the wishlist.
     */
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

    /**
     * A method that shows the report button if the book is rented by the user.
     */
    public void showReportButton(){
        int uid = Login.getUserLoggedIn().orElse(-1);
        if (uid == -1 || !new RentalRepository().isRentedByUser(uid, book.getBookId())){
            reportButton.setVisible(false);
            return;
        }
        reportButton.setVisible(true);
    }

    /**
     * A method that extends the rental of the book by one month.
     */
    @FXML
    protected void extendPressed() {
        // update rental
        RentalRepository repo = new RentalRepository();
        BookRental rental = repo.getCurrentBookRental(book.getBookId());
        var date = rental.getDateToReturn();
        date.setMonth(date.getMonth() + 1);
        rental.setDateToReturn(date);
        rental.setWasProlonged(true);
        repo.update(rental);

        // inform user about success
        actionButton.setDisable(true);
        actionLabel.setText("You have successfully extended your rental. It must be returned by " + date);
    }

    /**
     * A method that adds the user to the queue for the book.
     */
    @FXML
    protected void reservePressed() {
        java.sql.Date date;
        RentalRepository repo = new RentalRepository();
        // check if book has eny queue entries and get the last return date
        if (book.getStatus().equals(BookStatus.Rented)) {
            date = repo.getCurrentBookRental(book.getBookId()).getDateToReturn();
        } else {
            List<RentingQueue> queue = repo.getRentingQueuesByBookId(book.getBookId());
            date = queue.get(queue.size() - 1).getDateToReturn();
        }

        // create new queue entry
        var queue = new RentingQueue();
        queue.setBookId(book.getBookId());
        queue.setUserId(Login.getUserLoggedIn().get());
        queue.setDateToRent(date);
        java.sql.Date returnDate = (java.sql.Date) date.clone();
        returnDate.setMonth(returnDate.getMonth() + 1);
        queue.setDateToReturn(returnDate);
        repo.createRentingQueue(queue);

        // update book status if it's needed
        if (!book.getStatus().equals(BookStatus.ReadyForPickup)) {
            book.setStatus(BookStatus.Reserved);
            new BookRepository().update(book);
        }

        // inform user about success
        actionButton.setDisable(true);
        actionLabel.setText("You have successfully joined the queue. You can pick up the book on " + date);
    }

    /**
     * A method that ends the rental and returns the book.
     */
    @FXML
    protected void returnPressed() {
        // update book status
        if (book.getStatus().equals(BookStatus.Rented)) {
            book.setStatus(BookStatus.Available);
            actionButton.setDisable(true);
        } else {
            book.setStatus(BookStatus.ReadyForPickup);
        }
        new BookRepository().update(book);

        // update rental to returned
        RentalRepository repo = new RentalRepository();
        BookRental rental = repo.getCurrentBookRental(book.getBookId());
        rental.setDateReturned(new java.sql.Date(System.currentTimeMillis()));
        repo.update(rental);

        // inform user about success
        returnButton.setDisable(true);
        returnText.setText("");
        actionLabel.setText("You have successfully returned this book.");
    }

    /**
     * A method that removes the user from the queue.
     */
    @FXML
    protected void resignPressed() {
        // get the queue for the book
        RentalRepository repo = new RentalRepository();
        List<RentingQueue> queue = repo.getRentingQueuesByBookId(book.getBookId());
        if (queue.size() == 1) {
            // if the user is the only one in the queue, just remove his entry and update book status
            repo.deleteRentingQueue(queue.get(0));
            if (book.getStatus().equals(BookStatus.ReadyForPickup)) {
                book.setStatus(BookStatus.Available);
            } else {
                book.setStatus(BookStatus.Rented);
            }
            new BookRepository().update(book);
            actionButton.setDisable(true);
            actionLabel.setText("You have successfully left the queue.");
            return;
        }

        // Shift everyone behind the user in queue
        RentingQueue previous = null;
        int currentUid = Login.getUserLoggedIn().get();
        for (RentingQueue entry : queue) {
            if (entry.getUserId() == currentUid) {
                previous = entry;
                continue;
            }
            if (previous != null) {
                int uid = entry.getUserId();
                previous.setUserId(uid);
                repo.updateRentingQueue(previous);
                previous = entry;
            }
        }
        // Delete last entry
        repo.deleteRentingQueue(previous);

        // inform user about success
        actionButton.setDisable(true);
        actionLabel.setText("You have successfully left the queue.");
    }

    /**
     * A method that lets the first user in queue rent the book.
     */
    @FXML
    protected void pickupPressed() {
        // create new rental
        RentalRepository repo = new RentalRepository();
        BookRental rental = new BookRental();
        rental.setBookId(book.getBookId());
        rental.setUserId(Login.getUserLoggedIn().get());
        rental.setDateRented(new java.sql.Date(System.currentTimeMillis()));
        RentingQueue pos = repo.getRentingQueuesByBookId(book.getBookId()).get(0);
        var date = pos.getDateToReturn();
        rental.setDateToReturn(date);
        rental.setWasProlonged(false);
        repo.create(rental);

        // delete queue entry
        repo.deleteRentingQueue(pos);

        // update book status
        if (repo.getRentingQueuesByBookId(book.getBookId()).isEmpty()) {
            book.setStatus(BookStatus.Rented);
        } else {
            book.setStatus(BookStatus.Reserved);
        }
        new BookRepository().update(book);

        // inform about operation
        updateAction();
    }

    /**
     * A method that lets the user rent the book.
     */
    @FXML
    protected void rentPressed() {
        // create new rental
        BookRental rental = new BookRental();
        rental.setBookId(book.getBookId());
        rental.setUserId(Login.getUserLoggedIn().get());
        var rentDate = new java.sql.Date(System.currentTimeMillis());
        rental.setDateRented(rentDate);
        var returnDate = new java.sql.Date(System.currentTimeMillis());
        returnDate.setMonth(returnDate.getMonth() + 1);
        rental.setDateToReturn(returnDate);
        rental.setWasProlonged(false);
        new RentalRepository().create(rental);

        // update book status
        book.setStatus(BookStatus.Rented);
        new BookRepository().update(book);

        // inform user about success
        actionButton.setDisable(true);
        actionLabel.setText("You have successfully ordered this book. It must be returned by " + returnDate);
    }

    /**
     * A method that lets the user grade the book.
     */
    @FXML
    protected void gradeButtonPressed() {
        // create new grade
        int uid = Login.getUserLoggedIn().get();
        int bookId = book.getBookId();
        double gradeValue = gradeSlider.getValue();
        var grade = new BookGrade();
        grade.setBookId(bookId);
        grade.setUserId(uid);
        grade.setGrade(gradeValue);
        grade.setDateAdded(new java.sql.Date(System.currentTimeMillis()));
        new BookRepository().addBookGrade(grade);

        // inform user about success
        gradeButton.setDisable(true);
        gradeStatus.setText("You have already graded this book!");
        gradeSlider.setDisable(true);
    }

    /**
     * A method that updates the display of the book information.
     */
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

        // display cover image
        var repo = new BookRepository();
        if (repo.getById(book.getBookId()) != null) {
            String coverPath = repo.getById(book.getBookId()).getCover();
            var image = new Image(String.valueOf(Pap.class.getResource(coverPath)));
            imageView.setImage(image);
        }

        // display grade
        Pair<Integer, Double> pair = repo.getBookGradeCountAndAverageGrade(book.getBookId());
        if (pair == null) {
            grade.setText("Grade: 0.0 (this book hasn't been graded yet)");
            return;
        }
        grade.setText("Grade: " + pair.getValue() + " (based on " + pair.getKey() + " grades)");
    }

    /**
     * A method that updates the grading display.
     */
    private void updateGrading() {
        // check if user is logged in
        int uid = Login.getUserLoggedIn().orElse(-1);
        if (uid == -1) {
            gradeButton.setDisable(true);
            gradeStatus.setText("You must be logged in to do that!");
            gradeSlider.setDisable(true);
            gradeSlider.setValue(1);
            gradeText.setText("Your grade: 1.0");
            return;
        }

        // check if user has already graded this book
        var grade = new BookRepository().getThisBookGradeByUser(book.getBookId(), uid);
        if (grade != null) {
            gradeButton.setDisable(true);
            gradeStatus.setText("You have already graded this book!");
            gradeSlider.setDisable(true);
            gradeSlider.setValue(grade.getGrade());
            gradeText.setText("Your grade: " + grade.getGrade());
            return;
        }

        // set the grade slider to default position
        gradeButton.setDisable(false);
        gradeStatus.setText("");
        gradeSlider.setDisable(false);
        gradeSlider.setValue(1);
        gradeText.setText("Your grade: 1.0");
    }

    /**
     * A method that updates the action button and label.
     */
    private void updateAction() {
        returnButton.setVisible(false);
        returnText.setVisible(false);

        // first, check if the book is unavailable
        String status = book.getStatus();
        if (status.equals(BookStatus.Unavailable)) {        // G
            actionButton.setText("Rent");
            actionButton.setDisable(true);
            actionLabel.setText("This book is unavailable.");
            return;
        }

        // check if user is logged in
        int uid = Login.getUserLoggedIn().orElse(-1);
        if (uid == -1) {                                    // G
            actionButton.setText("Rent");
            actionButton.setDisable(true);
            actionLabel.setText("You must be logged in to do that!");
            return;
        }

        actionButton.setDisable(false);

        // check if book is available
        if (status.equals(BookStatus.Available)) {      // G
            actionButton.setText("Rent");
            actionButton.setOnAction(event -> rentPressed());
            if (new UserRepository().getById(uid).isHasUnpaidPenalty()) {
                // if user has unpaid penalties he cannot rent books
                actionButton.setDisable(true);
                actionLabel.setText("You cannot rent books until you pay your penalties."); // G
            } else {
                actionLabel.setText("Book is available and can be rented.");
            }
            return;
        }

        // check if the book is ready for pickup and user is first in queue
        RentalRepository repo = new RentalRepository();
        if (book.getStatus().equals(BookStatus.ReadyForPickup)) {
            RentingQueue queue = repo.getRentingQueuesByBookId(book.getBookId()).get(0);
            if (queue.getUserId() == uid) {
                actionButton.setText("Leave queue");
                actionButton.setOnAction(event -> resignPressed());
                actionLabel.setText("You are first in queue for this book. You can pick it up now.");
                returnButton.setText("Pick up");
                returnButton.setOnAction(event -> pickupPressed());
                returnButton.setVisible(true);
                returnText.setText("The time has come for you to pick up this book.");
                returnText.setVisible(true);
                returnButton.setDisable(false);
                return;
            }
        }

        // check if user rented this book
        BookRental rental = repo.getCurrentBookRental(book.getBookId());
        if (rental != null && rental.getUserId() == uid) {                // G
            // set return button
            returnButton.setText("Return");
            returnButton.setVisible(true);
            returnButton.setOnAction(event -> returnPressed());
            var returnDate = rental.getDateToReturn();
            returnText.setText("You are currently renting this book (must be returned on " + returnDate + ")");
            returnText.setVisible(true);

            // action button setting
            actionButton.setText("Extend");
            actionButton.setOnAction(event -> extendPressed());

            // check if user has unpaid penalties, he cannot extend a rental if he has
            if (new UserRepository().getById(uid).isHasUnpaidPenalty()) {
                actionButton.setDisable(true);
                actionLabel.setText("You cannot extend your rental until you pay your penalties."); // G
                return;
            }

            // check if book rental can be extended
            if (status.equals(BookStatus.Rented)) {
                // check if the rental has already been extented
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

        // check if user has an unpaid penalty, he cannot join the queue if he has
        if (new UserRepository().getById(uid).isHasUnpaidPenalty()) {
            actionButton.setText("Join the queue");
            actionButton.setDisable(true);
            actionLabel.setText("You cannot join queues until you pay your penalties.");
            return;
        }

        // check if there is anyone in queue for this book
        List<RentingQueue> queue = repo.getRentingQueuesByBookId(book.getBookId());
        if (queue.isEmpty()) {
            actionButton.setText("Reserve");    // G
            actionButton.setOnAction(event -> reservePressed());
            actionLabel.setText("This book is currently rented until " + rental.getDateToReturn() + ". You can reserve it and pick it up on that day.");
            return;
        }

        // check if user is in queue
        for (RentingQueue userEntry : queue) {
            if (userEntry.getUserId() == uid) {             // G
                var pickupDate = userEntry.getDateToRent();
                actionButton.setText("Leave the queue");
                actionButton.setOnAction(event -> resignPressed());
                actionLabel.setText("You are currently in queue for this book (should be available on " + pickupDate + ").");
                return;
            }
        }

        actionButton.setText("Join the queue");
        actionButton.setOnAction(event -> reservePressed());
        var lastDate = queue.get(queue.size() - 1).getDateToReturn();
        // check if queue is full
        if (queue.size() < Parameters.getMaxQueueLength()) {
            actionLabel.setText("This book is reserved until " + lastDate + ". You can join the queue and pick it up on the day after.");
        } else {
            actionButton.setDisable(true);
            actionLabel.setText("This book is reserved until " + lastDate + ", but the queue is full so you cannot join it.");
        }
    }

    /**
     * A method that adds the book to the users wishlist or removes it from it if it's already in.
     */
    public void wishButtonClicked(MouseEvent mouseEvent){
        // check if user is logged in
        int uid = Login.getUserLoggedIn().orElse(-1);
        if (uid == -1){
            wishButton.setText("Add a book to the wishlist");
            wishLabel.setText("Login to add a book to wishlist");
            return;
        }

        // check if the book is already in the wishlist
        BookWishList wish = new BookWishList();
        WishRepository wishRepo = new WishRepository();

        // check if the wish already exists, if not create it, if yes delete it
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

    /**
     * A method that allows the user to report the book.
     */
    public void reportButtonClicked(MouseEvent mouseEvent){
        // check if user has already reported the book
        BookReport existingReport = new ReportRepository().getReportByUserAndBook(Login.getUserLoggedIn().get(), book.getBookId());
        if (existingReport == null) {
            BookReportController.setBook(book);
            BookReportController.setUserId(Login.getUserLoggedIn().get());
            GlobalController.switchVisibleContent(LoadedPages.bookReport);
        } else {
            // inform user about the status of his report
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
        contentPane.setMaxSize(Pap.getStage().getMinWidth(), Pap.getStage().getMinHeight());
        update();
    }
}
