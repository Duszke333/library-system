package pap.controllers;

import pap.db.Entities.Book;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import pap.db.Repository.BookRepository;
import pap.helpers.ConstraintChecker;

import java.io.File;

public class BookCreatorController implements UpdatableController {
    @FXML
    private TextField bookISBN;
    @FXML
    private TextField bookTitle;
    @FXML
    private TextField bookAuthor;
    @FXML
    private TextField bookGenre;
    @FXML
    private TextField bookPublicationYear;
    @FXML
    private TextField bookLanguage;
    @FXML
    private TextField bookPageCount;
    @FXML
    private TextField bookPublisher;
    @FXML
    private TextArea bookDescription;
    @FXML
    private Text statusMessage;
    @FXML
    private TextField bookCover;

    @FXML
    protected void addBook() {
        String isbn = bookISBN.getText();
        String title = bookTitle.getText();
        String author = bookAuthor.getText();
        String genre = bookGenre.getText();
        String publication = bookPublicationYear.getText();
        String language = bookLanguage.getText();
        String pages = bookPageCount.getText();
        String publisher = bookPublisher.getText();
        String description = bookDescription.getText();
        String cover = bookCover.getText();

        if (isbn.isEmpty() || title.isEmpty() || author.isEmpty() || genre.isEmpty() || publication.isEmpty()
                || language.isEmpty() || pages.isEmpty() || publisher.isEmpty()) {
            statusMessage.setText("All fields except Description and Book cover must be filled!");
            statusMessage.setVisible(true);
            return;
        }
        int publicationYear;
        int pageCount;
        try {
            publicationYear = Integer.parseInt(publication);
            pageCount = Integer.parseInt(pages);
        } catch (NumberFormatException e) {
            statusMessage.setText("Publication year and page count must be integers!");
            statusMessage.setVisible(true);
            return;
        }

        Book book = new Book();
        book.setIsbn(isbn);
        book.setTitle(title);
        book.setAuthor(author);
        book.setGenre(genre);
        book.setPublicationYear(publicationYear);
        book.setLanguage(language);
        book.setPageCount(pageCount);
        book.setPublisher(publisher);
        if (description.isEmpty()) description = "Description will be added soon.";
        book.setDescription(description);
        book.setDateAdded(new java.sql.Date(System.currentTimeMillis()));

        book.setStatus("Available");
        if (!cover.isEmpty()) {
            File f = new File(Book.CoverData.CoverPath + cover);
            if (f.exists() && !f.isDirectory()) {
                book.setCover(Book.CoverData.CoverFolder + cover);
            } else {
                book.setCover(Book.CoverData.DefaultCover);
            }
        } else {
            book.setCover(Book.CoverData.DefaultCover);
        }

        BookRepository bookRepo = new BookRepository();
        int error = ConstraintChecker.checkBook(book);
        if (error != -1) {
            statusMessage.setText("Error: " + ConstraintChecker.BookErrors.values()[error].toString());
            statusMessage.setVisible(true);
            return;
        }
        bookRepo.create(book);
        statusMessage.setText("Book added!");
        statusMessage.setFill(javafx.scene.paint.Color.GREEN);
        update();
        statusMessage.setVisible(true);
    }

    @Override
    public void update() {
        statusMessage.setVisible(false);
        bookISBN.clear();
        bookTitle.clear();
        bookAuthor.clear();
        bookGenre.clear();
        bookPublicationYear.clear();
        bookLanguage.clear();
        bookPageCount.clear();
        bookPublisher.clear();
        bookCover.clear();
        bookDescription.clear();
    }
}
