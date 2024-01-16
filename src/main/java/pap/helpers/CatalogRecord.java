package pap.helpers;

import javafx.util.Pair;
import lombok.Getter;
import pap.db.Entities.Book;
import pap.db.Repository.BookRepository;

import java.util.List;

@Getter
public class CatalogRecord {
    /**
     * A class that represents a record of the TableView in browse-catalog page.
     */
    private int bookId;
    private String title;
    private String author;
    private String language;
    private String genre;
    private int numberOfGrades;
    private double averageGrade;

    /**
     * Constructor for the CatalogRecord class.
     * @param book Book object that is used to create the record.
     */
    public CatalogRecord(Book book) {
        this.bookId = book.getBookId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.language = book.getLanguage();
        this.genre = book.getGenre();

        // check if the book has any grades
        Pair<Integer, Double> pair = new BookRepository().getBookGradeCountAndAverageGrade(bookId);
        if (pair == null) {
            this.numberOfGrades = 0;
            this.averageGrade = 0;
            return;
        }
        this.numberOfGrades = pair.getKey();
        this.averageGrade = pair.getValue();
    }

    /**
     * A method that returns a list of all books.
     * @return list of all books
     */
    public static List<CatalogRecord> getAll() {
        // get all the books
        List<Book> books = new BookRepository().getAll();

        // create a list of records from the books
        List<CatalogRecord> records = new java.util.ArrayList<>();
        for (Book book : books) {
            records.add(new CatalogRecord(book));
        }
        return records;
    }
}
