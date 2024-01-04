package pap.db.Repository.Interface;
import pap.db.Entities.BookWishList;

import java.util.List;

public interface IBookWishList extends IRepository<BookWishList> {
    List<BookWishList> getWishListByUserId(int id);
}
