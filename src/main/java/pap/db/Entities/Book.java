package pap.db.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "BOOKS", schema = "pap", catalog = "postgres")
public class Book implements java.io.Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id", nullable = false)
    private int bookId;
    @Basic
    @Column(name = "isbn", nullable = false, length = 13)
    private String isbn;
    @Basic
    @Column(name = "title", nullable = false, length = 128)
    private String title;
    @Basic
    @Column(name = "author", nullable = false, length = 128)
    private String author;
    @Basic
    @Column(name = "genre", nullable = false, length = 128)
    private String genre;
    @Basic
    @Column(name = "publication_year", nullable = false)
    private int publicationYear;
    @Basic
    @Column(name = "language", nullable = false, length = 64)
    private String language;
    @Basic
    @Column(name = "page_count", nullable = false)
    private int pageCount;
    @Basic
    @Column(name = "publisher", nullable = false, length = 128)
    private String publisher;
    @Basic
    @Column(name = "status", nullable = false, length=32)
    private String status;
    @Basic
    @Column(name = "description", nullable = false, length = -1)
    private String description;
    @Basic
    @Column(name = "date_added", nullable = false)
    private Date dateAdded;
    @Basic
    @Column(name = "cover", nullable = false, length = 256)
    private String cover;

    @Data
    public static class BookStatus {
        public final static String Available = "Available";
        public final static String Unavailable = "Unavailable";
        public final static String Reserved = "Reserved";
        public final static String Rented = "Rented";
        public final static String ReadyForPickup = "Ready for pickup";
    }
    @Data
    public static class CoverData {
        public final static String DefaultCover = "images/default_cover.png";
        public final static String CoverPath = "src/main/resources/pap/images/";
        public final static String CoverFolder = "images/";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (bookId != book.bookId) return false;
        if (publicationYear != book.publicationYear) return false;
        if (pageCount != book.pageCount) return false;
        if (!Objects.equals(status, book.status)) return false;
        if (!Objects.equals(isbn, book.isbn)) return false;
        if (!Objects.equals(title, book.title)) return false;
        if (!Objects.equals(author, book.author)) return false;
        if (!Objects.equals(genre, book.genre)) return false;
        if (!Objects.equals(language, book.language)) return false;
        if (!Objects.equals(publisher, book.publisher)) return false;
        if (!Objects.equals(description, book.description)) return false;
        return Objects.equals(dateAdded, book.dateAdded);
    }

    @Override
    public int hashCode() {
        int result = bookId;
        result = 31 * result + (isbn != null ? isbn.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (genre != null ? genre.hashCode() : 0);
        result = 31 * result + publicationYear;
        result = 31 * result + (language != null ? language.hashCode() : 0);
        result = 31 * result + pageCount;
        result = 31 * result + (publisher != null ? publisher.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (dateAdded != null ? dateAdded.hashCode() : 0);
        return result;
    }
}
