package pap.db.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "wishlist", schema = "pap", catalog = "pap")
public class BookWishList {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "wish_id")
    private int wishListId;
    @Basic
    @Column(name = "book_id")
    private int bookId;
    @Basic
    @Column(name = "user_id")
    private int userId;
    @Basic
    @Column(name = "date_added")
    private Date dateAdded;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookWishList)) return false;
        BookWishList wishList = (BookWishList) o;
        return getWishListId() == wishList.getWishListId() && getUserId() == wishList.getUserId() && getBookId() == wishList.getBookId() && getDateAdded().equals(wishList.getDateAdded());
    }


}


