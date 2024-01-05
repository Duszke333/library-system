package pap.db.Repository;

import pap.db.DAO.EntityDAO.BookWishListDAO;
import pap.db.Entities.BookWishList;
import pap.db.Repository.Interface.IBookWishList;

import java.util.List;

public class WishRepository implements IBookWishList {
    private BookWishListDAO bookWishListDAO = new BookWishListDAO();

    @Override
    public List<BookWishList> getWishListByUserId(int id) {
        String sql = "SELECT * FROM pap.wish_list WHERE user_id = " + id;
        return bookWishListDAO.query(sql);
    }

    @Override
    public BookWishList getWishListByUserAndBook(int userId, int bookId) {
        String sql = "SELECT * FROM pap.wish_list WHERE user_id = " + userId + " AND book_id = " + bookId;
        List<BookWishList> wishList =  bookWishListDAO.query(sql);
        if (!wishList.isEmpty()){
            return wishList.get(0);
        }else{
            return  null;
        }
    }

    @Override
    public List<BookWishList> getAll() {
        return bookWishListDAO.getAll();
    }

    @Override
    public BookWishList getById(int id) {
        return bookWishListDAO.read(id);
    }

    @Override
    public void create(BookWishList entity) {
        bookWishListDAO.create(entity);
    }

    @Override
    public void update(BookWishList entity) {
        bookWishListDAO.update(entity);
    }

    @Override
    public void delete(BookWishList entity) {
        bookWishListDAO.delete(entity);
    }


}
