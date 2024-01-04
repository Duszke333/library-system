package pap.db;

import pap.db.Entities.BookGrade;
import pap.db.Repository.BookRepository;

import java.sql.Date;
import java.util.Scanner;



public class TestDatabase {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        BookRepository bookRepository = new BookRepository();

        BookGrade bookGrade = new BookGrade();
        bookGrade.setBookId(1);
        bookGrade.setUserId(1);
        bookGrade.setGrade(5);
        bookGrade.setDateAdded(new Date(System.currentTimeMillis()));
        bookRepository.addBookGrade(bookGrade);

        BookGrade newBookGrade = bookRepository.getBookGrade(6);
        System.out.println(newBookGrade.getGrade());
    }
}
