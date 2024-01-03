package pap.helpers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import pap.Pap;
import pap.controllers.BookViewController;
import pap.controllers.GlobalController;
import pap.db.Entities.Book;

import java.io.IOException;

public class BookViewLoader {
    public static void load(Book chosenBook) {
        try {
            FXMLLoader bookLoader = new FXMLLoader(Pap.class.getResource("view/book-view.fxml"));
            Parent bookRoot = bookLoader.load();
            BookViewController bookViewController = bookLoader.getController();

            bookViewController.setBook(chosenBook);
            bookViewController.setBook(chosenBook);
            bookViewController.displayTitle(chosenBook.getTitle());
            bookViewController.displayAuthor(chosenBook.getAuthor());
            bookViewController.displayGenre(chosenBook.getGenre());
            bookViewController.displayPublicationYear(chosenBook.getPublicationYear());
            bookViewController.displayLanguage(chosenBook.getLanguage());
            bookViewController.displayPageCount(chosenBook.getPageCount());
            bookViewController.displayPublisher(chosenBook.getPublisher());
            bookViewController.displayDescription(chosenBook.getDescription());
            bookViewController.displayAvailability(chosenBook.getStatus());
            bookViewController.displayDateAdded(chosenBook.getDateAdded());

            GlobalController.switchVisibleContent(LoadedPages.bookViewController, bookRoot);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
