package pap.helpers;

import lombok.Getter;
import pap.db.Entities.Book;
import pap.db.Entities.BookWishList;
import pap.db.Repository.BookRepository;
import pap.db.Repository.WishRepository;

import java.util.List;


public class WishRecord {
    @Getter
    private int wishId;
    @Getter
    private int bookId;
    @Getter
    private int userId;
    @Getter
    private String dateAdded;
    @Getter
    private String title;
    @Getter
    private String author;
    @Getter
    private String language;

    public WishRecord(BookWishList rental) {
        this.wishId = rental.getWishListId();
        this.bookId = rental.getBookId();
        this.userId = rental.getUserId();
        this.dateAdded = rental.getDateAdded().toString();

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