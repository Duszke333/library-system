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

    /**
     * Constructor for the WishRecord class.
     * @param wishList BookWishList object that is used to create the record.
     */
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

    /**
     * A method that returns a list of WishRecords from all books in the user's wish list.
     * @param userId id of the user whose wish list is searched for
     * @return list of all books in the user's wish list
     */
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