package pap.db.DAO.EntityDAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pap.db.DAO.GenericDAO;
import pap.db.Entities.BookWishList;

import java.util.List;

public class BookWishListDAO extends GenericDAO<BookWishList> {

    public BookWishListDAO() {
        super(BookWishList.class, "pap.wishlist");
    }

    public BookWishListDAO(SessionFactory factory) {
        super(BookWishList.class, "pap.wishlist", factory);
    }
}