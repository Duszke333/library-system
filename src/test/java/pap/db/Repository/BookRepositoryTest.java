package pap.db.Repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pap.db.Entities.Book;
import pap.db.Entities.BookGrade;
import pap.db.Entities.ReadList;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookRepositoryTest {
    BookRepository bookRepository = new BookRepository();

    @Test
    void getAll() {
        List<Book> books = bookRepository.getAll();
        Assertions.assertNotNull(books);
    }

    @Test
    void getById() {
        Book book = bookRepository.getById(1);
        Assertions.assertNotNull(book);
    }

    @Test
    void getBookGrade() {
        BookGrade bookGrade = bookRepository.getBookGrade(1);
        Assertions.assertNotNull(bookGrade);
    }

    @Test
    void getAllBookGrades() {
        List<BookGrade> bookGrades = bookRepository.getAllBookGrades();
        Assertions.assertNotNull(bookGrades);
    }

    @Test
    void getReadList() {
        ReadList readLists = bookRepository.getReadList(1);
        Assertions.assertNotNull(readLists);
    }
}