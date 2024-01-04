package pap.controllers;

import javafx.scene.control.TextArea;
import pap.db.Entities.Book;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import lombok.Setter;

import static pap.db.Entities.Book.*;


import javafx.scene.input.MouseEvent;
import pap.db.Entities.BookRental;
import pap.db.Entities.BookWishList;
import pap.db.Repository.BookRepository;
import pap.db.Repository.RentalRepository;
import pap.db.Repository.WishRepository;
import pap.helpers.Login;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BookViewController implements UpdatableController {
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

    @FXML
    Label wishLabel;

    @Setter
    Book book;

    @FXML
    Button orderButton;

    @FXML
    Button wishButton;


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

    public void displayAvailability(String status) {
        isAvailableLabel.setText("Dostępność: " + status);
    }

    public void displayWishStatus() {
        int logged = 0;
        try {
            logged = Login.getUserLoggedIn().get();
        } catch (Exception e) {
            return;
        }
        if(!(new WishRepository().getWishListByUserAndBook(logged, book.getBookId()).isEmpty())){
            wishButton.setText("Remove from wish list");
        }
    }

    public void displayDateAdded(Date dateAdded) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        dateAddedLabel.setText("Data dodania: " + dateFormat.format(dateAdded));
    }


    public void orderButtonClicked(MouseEvent mouseEvent){
        if(book.getStatus().equals(Book.BookStatus.Available)){
            int logged = 0;
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

    public void wishButtonClicked(MouseEvent mouseEvent){
        int logged = 0;
        try {
            logged = Login.getUserLoggedIn().get();
        } catch (Exception e) {
            wishLabel.setText("Musisz być zalogowany, aby dodać do listy życzeń");
            return;
        }
        BookWishList wish = new BookWishList();
        WishRepository wish_repo = new WishRepository();
        List<BookWishList> wishListByUserAndBook = wish_repo.getWishListByUserAndBook(logged, book.getBookId());
        if (wishListByUserAndBook.isEmpty()) {
            wish.setBookId(book.getBookId());
            wish.setUserId(logged);
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
    }
}
