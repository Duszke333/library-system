package db.Repository;

import db.Entities.Book;

import java.util.List;

public interface BookRepository extends Repository<Book>{
    Book getByTitle(String title);
    Book getByISBN(String ISBN);
    Book getByAuthor(String author);
    Book getByGenre(String genre);
    Book getByDateAdded(String dateAdded);
    Book getByDatePublished(String datePublished);
    List<Book> getAllByLanguage(String language);
    List<Book> getAllAvailable();
    List<Book> getAll();
}
