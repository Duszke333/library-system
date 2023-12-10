package db.Repository;

import db.Entities.Book;

import java.util.List;

public interface IBookRepository extends IRepository<Book> {
    Book getByTitle(String title);
    Book getByISBN(String ISBN);
    List<Book> getByAuthor(String author);
    List<Book> getByGenre(String genre);
    List<Book> getByDateAdded(String dateAdded);
    List<Book> getByDatePublished(String datePublished);
    List<Book> getAllByLanguage(String language);
    List<Book> getAllAvailable();
}
