package pap.helpers;

import lombok.Getter;
import pap.db.Entities.Book;
import pap.db.Entities.BookWishList;
import pap.db.Repository.BookRepository;
import pap.db.Repository.WishRepository;

import java.util.List;


@Getter
public class WishRecord {
    /**
     * A class that represents a record of the TableView in browse-wish-list page.
     */
    private int wishId;
    private int bookId;
    private int userId;
    private String dateAdded;
    private String title;
    private String author;
    private String language;

    public WishRecord(BookWishList wishList) {
        this.wishId = wishList.getWishListId();
        this.bookId = wishList.getBookId();
        this.userId = wishList.getUserId();
        this.dateAdded = wishList.getDateAdded().toString();

        Book book = new BookRepository().getById(bookId);
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.language = book.getLanguage();
    }

    public static List<WishRecord> getWishList(int userId) {
        List<BookWishList> raw = new WishRepository().getWishListByUserId(userId);
        List<WishRecord> records = new java.util.ArrayList<>();
        if (raw != null){
        for (BookWishList wish : raw) {
            WishRecord record = new WishRecord(wish);
            records.add(record);
        }
        }

        return records;
    }

}