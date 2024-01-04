package pap.helpers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import pap.Pap;
import pap.controllers.BookViewController;
import pap.controllers.GlobalController;
import pap.db.Entities.Book;

import java.io.IOException;

@Deprecated
public class BookViewLoader {
    public static void load(Book chosenBook) {
        BookViewController.setBook(chosenBook);
        GlobalController.switchVisibleContent(LoadedPages.bookViewController, LoadedPages.bookView);
    }
}
