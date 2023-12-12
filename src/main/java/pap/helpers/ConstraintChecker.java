package pap.helpers;

import pap.db.Entities.Address;
import pap.db.Entities.Book;
import pap.db.Entities.User;
import pap.db.Repository.BookRepository;

import java.time.Year;

public class ConstraintChecker {
    public enum BookErrors {
        ISBN_TOO_LONG, TITLE_TOO_LONG, AUTHOR_TOO_LONG,
        GENRE_TOO_LONG, PUBLISHER_TOO_LONG, LANGUAGE_TOO_LONG,
        WRONG_STATUS, WRONG_PUBLICATION_YEAR, WRONG_PAGE_COUNT
    }

    public static int checkBook(Book book) {
        if (book.getIsbn().length() >= 32) return BookErrors.ISBN_TOO_LONG.ordinal();
        if (book.getTitle().length() >= 128) return BookErrors.TITLE_TOO_LONG.ordinal();
        if (book.getAuthor().length() >= 128) return BookErrors.AUTHOR_TOO_LONG.ordinal();
        if (book.getGenre().length() >= 128) return BookErrors.GENRE_TOO_LONG.ordinal();
        if (book.getPublisher().length() >= 128) return BookErrors.PUBLISHER_TOO_LONG.ordinal();
        if (book.getLanguage().length() >= 128) return BookErrors.LANGUAGE_TOO_LONG.ordinal();
        if (!book.getStatus().equals("Available") && !book.getStatus().equals("Unavailable") && !book.getStatus().equals("Reserved")) {
            return BookErrors.WRONG_STATUS.ordinal();
        }
        if (book.getPublicationYear() > Year.now().getValue()) return BookErrors.WRONG_PUBLICATION_YEAR.ordinal();
        if (book.getPageCount() <= 0) return BookErrors.WRONG_PAGE_COUNT.ordinal();
        return -1;
    }

    public enum AddressErrors {
        COUNTRY_TOO_LONG, POSTAL_CODE_TOO_LONG, CITY_TOO_LONG,
        STREET_TOO_LONG, HOUSE_NUMBER_TOO_LONG, FLAT_NUMBER_TOO_LONG
    }

    public static int checkAddress(Address address) {
        if (address.getCountry().length() >= 64) return AddressErrors.COUNTRY_TOO_LONG.ordinal();
        if (address.getPostalCode().length() >= 16) return AddressErrors.POSTAL_CODE_TOO_LONG.ordinal();
        if (address.getCity().length() >= 64) return AddressErrors.CITY_TOO_LONG.ordinal();
        if (address.getStreet().length() >= 64) return AddressErrors.STREET_TOO_LONG.ordinal();
        if (address.getHouseNumber().length() >= 16) return AddressErrors.HOUSE_NUMBER_TOO_LONG.ordinal();
        if (address.getFlatNumber().length() >= 16) return AddressErrors.FLAT_NUMBER_TOO_LONG.ordinal();
        return -1;
    }
}
