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
    @Column(name = "is_available", nullable = false)
    private boolean isAvailable;
    @Basic
    @Column(name = "description", nullable = false, length = -1)
    private String description;
    @Basic
    @Column(name = "date_added", nullable = false)
    private Date dateAdded;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (bookId != book.bookId) return false;
        if (publicationYear != book.publicationYear) return false;
        if (pageCount != book.pageCount) return false;
        if (isAvailable != book.isAvailable) return false;
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
        result = 31 * result + (isAvailable ? 1 : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (dateAdded != null ? dateAdded.hashCode() : 0);
        return result;
    }
}
