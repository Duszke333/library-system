package pap.helpers;

import pap.controllers.BookViewController;
import pap.controllers.GlobalController;
import pap.db.Entities.Book;

@Deprecated
public class BookViewLoader {
    public static void load(Book chosenBook) {
        BookViewController.setBook(chosenBook);
        GlobalController.switchVisibleContent(LoadedPages.bookView);
    }
}
