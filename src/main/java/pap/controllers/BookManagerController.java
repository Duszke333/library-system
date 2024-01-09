package pap.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import pap.db.Entities.Book;
import pap.db.Repository.BookRepository;
import pap.helpers.ConstraintChecker;
import pap.helpers.LoadedPages;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class BookManagerController implements UpdatableController, Initializable {
    private Book book;
    @FXML
    private TextField isbnInput;
    @FXML
    private TextField titleInput;
    @FXML
    private TextField authorInput;
    @FXML
    private TextField genreInput;
    @FXML
    private TextField publicationYearInput;
    @FXML
    private TextField languageInput;
    @FXML
    private TextField pageCountInput;
    @FXML
    private TextField publisherInput;
    @FXML
    private TextArea descriptionInput;
    @FXML
    private Text updateStatus;
    @FXML
    private Text deletionStatus;
    @FXML
    private Button deletionButton;
    @FXML
    private Button confirmDeletion;
    @FXML
    private Button cancelDeletion;
    @FXML
    private TextField coverInput;

    @FXML
    protected void updateInformation() {
        updateStatus.setFill(javafx.scene.paint.Color.RED);
        updateStatus.setVisible(false);
        String isbn = isbnInput.getText();
        String title = titleInput.getText();
        String author = authorInput.getText();
        String genre = genreInput.getText();
        String publicationYear = publicationYearInput.getText();
        String language = languageInput.getText();
        String pageCount = pageCountInput.getText();
        String publisher = publisherInput.getText();
        String description = descriptionInput.getText();
        String cover = coverInput.getText();

        if (isbn.isEmpty() || title.isEmpty() || author.isEmpty() || genre.isEmpty() || publicationYear.isEmpty()
                || language.isEmpty() || pageCount.isEmpty() || publisher.isEmpty()) {
            updateStatus.setText("All fields except Description must be filled!");
            updateStatus.setVisible(true);
            return;
        }

        Integer year;
        Integer pages;
        try {
            year = Integer.parseInt(publicationYear);
            pages = Integer.parseInt(pageCount);
        } catch (NumberFormatException e) {
            updateStatus.setText("Publication Year and Page Count must be numbers!");
            updateStatus.setVisible(true);
            return;
        }

        if (!cover.isEmpty()) {
            File f = new File(Book.CoverData.CoverPath + cover);
            if (!f.exists() || f.isDirectory()) {
                updateStatus.setText("Cover file does not exist!");
                updateStatus.setVisible(true);
                return;
            } else {
                cover = Book.CoverData.CoverFolder + cover;
            }
        } else {
            cover = Book.CoverData.DefaultCover;
        }

        book.setIsbn(isbn);
        book.setTitle(title);
        book.setAuthor(author);
        book.setGenre(genre);
        book.setPublicationYear(year);
        book.setLanguage(language);
        book.setPageCount(pages);
        book.setPublisher(publisher);
        if (description.isEmpty()) description = "Description will be added soon.";
        book.setDescription(description);
        book.setCover(cover);

        BookRepository bookRepo = new BookRepository();
        int error = ConstraintChecker.checkBook(book);
        if (error != -1) {
            updateStatus.setText("Error: " + ConstraintChecker.BookErrors.values()[error].toString());
            updateStatus.setVisible(true);
            return;
        }
        bookRepo.update(book);
        updateStatus.setText("Book updated successfully!");
        updateStatus.setFill(javafx.scene.paint.Color.GREEN);
        updateStatus.setVisible(true);
    }

    @FXML
    protected void deleteBook() {
        deletionStatus.setFill(javafx.scene.paint.Color.WHITE);
        deletionStatus.setText("Are you sure you want to delete this book?");
        deletionStatus.setVisible(true);
        deletionButton.setDisable(true);
        confirmDeletion.setVisible(true);
        cancelDeletion.setVisible(true);
    }

    @FXML
    protected void viewHistory() {
        BrowseBookHistoryController.setBookId(book.getBookId());
        GlobalController.switchVisibleContent(LoadedPages.browseBookHistory);
    }

    @FXML
    protected void deletionConfirmed() {
        deletionStatus.setFill(javafx.scene.paint.Color.RED);
        deletionStatus.setText("Book deleted.");
        confirmDeletion.setVisible(false);
        cancelDeletion.setVisible(false);
        new BookRepository().delete(book);
    }

    @FXML
    protected void deletionCancelled() {
        deletionStatus.setFill(javafx.scene.paint.Color.GREEN);
        deletionStatus.setText("Book deletion cancelled.");
        deletionButton.setDisable(false);
        confirmDeletion.setVisible(false);
        cancelDeletion.setVisible(false);
    }

    @FXML
    protected void goBack() {
        GlobalController.switchVisibleContent(LoadedPages.manageCatalog);
    }
    
    @Override
    public void update() {
        book = new BookRepository().getById(ManageCatalogController.chosenBookID);
        isbnInput.setText(book.getIsbn());
        titleInput.setText(book.getTitle());
        authorInput.setText(book.getAuthor());
        genreInput.setText(book.getGenre());
        publicationYearInput.setText(String.valueOf(book.getPublicationYear()));
        languageInput.setText(book.getLanguage());
        pageCountInput.setText(String.valueOf(book.getPageCount()));
        publisherInput.setText(book.getPublisher());
        descriptionInput.setText(book.getDescription());
        coverInput.setText(book.getCover().substring("images/".length()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        update();
    }
}
