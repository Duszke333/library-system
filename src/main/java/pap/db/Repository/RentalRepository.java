package pap.db.Repository;

import pap.db.DAO.EntityDAO.BookRentalDAO;
import pap.db.DAO.EntityDAO.PenaltyDAO;
import pap.db.DAO.EntityDAO.ReadListDAO;
import pap.db.DAO.EntityDAO.RentingQueueDAO;
import pap.db.DAO.GenericDAO;
import pap.db.Entities.BookRental;
import pap.db.Entities.Penalty;
import pap.db.Entities.RentingQueue;
import pap.db.Repository.Interface.IRentalRepository;

import java.util.List;

public class RentalRepository extends GenericRepository<BookRental> implements IRentalRepository {
    private BookRentalDAO bookRentalDAO = new BookRentalDAO();
    private PenaltyDAO penaltyDAO = new PenaltyDAO();
    private RentingQueueDAO rentingQueueDAO = new RentingQueueDAO();

    public RentalRepository() {
        super(BookRental.class, new BookRentalDAO());
    }

    @Override
    public List<BookRental> getRentalsByBookId(int id) throws NullPointerException {
        String sql = "SELECT * FROM pap.book_rentals WHERE book_id = " + id;
        List<BookRental> list = bookRentalDAO.query(sql);
        if (list.size() == 0) {
            throw new NullPointerException("Rentals for book with id " + id + " not found");
        }
        return list;
    }
    @Override
    public List<BookRental> getRentalsByUserId(int id) throws NullPointerException {
        String sql = "SELECT * FROM pap.book_rentals WHERE user_id = " + id;
        List<BookRental> list = bookRentalDAO.query(sql);
        if (list.size() == 0) {
            throw new NullPointerException("Rentals for user with id " + id + " not found");
        }
        return list;
    }

    public List<BookRental> getAllExceededRentals() throws NullPointerException {
        String sql = "SELECT * FROM pap.book_rentals WHERE date_returned IS NULL AND date_to_return < NOW()";
        List<BookRental> list = bookRentalDAO.query(sql);
        if (list.size() == 0) {
            throw new NullPointerException("No exceeded rentals found");
        }
        return list;
    }

    public BookRental getCurrentBookRental(int id) throws NullPointerException {
        String sql = "SELECT * FROM pap.book_rentals WHERE book_id = " + id + " AND date_returned IS NULL";
        List<BookRental> res = bookRentalDAO.query(sql);
        if (res == null || res.isEmpty()) {
            throw new NullPointerException("No rentals found for book with id " + id);
        }
        return res.get(0);
    }

    public List<BookRental> getUserCurrentRentals(int id) throws NullPointerException {
        String sql = "SELECT * FROM pap.book_rentals WHERE user_id = " + id + " AND date_returned IS NULL";
        List<BookRental> res = bookRentalDAO.query(sql);
        if (res == null || res.isEmpty()) {
            throw new NullPointerException("No rentals found for user with id " + id);
        }
        return res;
    }

    public BookRental getLastBookRental(int id) throws NullPointerException {
        String sql = "SELECT * FROM pap.book_rentals WHERE book_id = " + id + " ORDER BY date_rented DESC LIMIT 1";
        List<BookRental> res = bookRentalDAO.query(sql);
        if (res == null || res.isEmpty()) {
            throw new NullPointerException("No rentals found for book with id " + id);
        }
        return res.get(0);
    }

    @Override
    public boolean isRentedByUser(int userId, int bookId) {
        String sql = "SELECT * FROM pap.book_rentals WHERE user_id = " + userId + " AND book_id = " + bookId + " AND date_returned is null";
        return !bookRentalDAO.query(sql).isEmpty();
    }
    @Override
    public Penalty getPenaltyById(int id) throws NullPointerException {
        Penalty res =  penaltyDAO.read(id);
        if (res == null) {
            throw new NullPointerException("Penalty with id " + id + " not found");
        }
        return res;
    }

    @Override
    public List<Penalty> getAllPenalties() {
        return penaltyDAO.getAll();
    }

    @Override
    public List<Penalty> getPenaltiesByUserId(int id) throws NullPointerException {
        String sql = "SELECT * FROM pap.penalties WHERE user_id = " + id;
        List<Penalty> res = penaltyDAO.query(sql);
        if (res == null || res.isEmpty()) {
            throw new NullPointerException("No penalties found for user with id " + id);
        }
        return res;
    }

    public List<Penalty> getUnpaidUserPenalties(int id) throws NullPointerException {
        String sql = "SELECT * FROM pap.penalties WHERE user_id = " + id + " AND date_paid IS NULL";
        List<Penalty> res = penaltyDAO.query(sql);
        if (res == null || res.isEmpty()) {
            throw new NullPointerException("No penalties found for user with id " + id);
        }
        return res;
    }

    @Override
    public List<Penalty> getPenaltiesByBookId(int id) throws NullPointerException {
        String sql = "SELECT * FROM pap.penalties WHERE book_id = " + id;
        List<Penalty> res = penaltyDAO.query(sql);
        if (res == null || res.isEmpty()) {
            throw new NullPointerException("No penalties found for book with id " + id);
        }
        return res;
    }

    public Penalty getPenaltyByRentalId(int id) throws NullPointerException {
        String sql = "SELECT * FROM pap.penalties WHERE rental_id = " + id + " AND date_paid IS NULL";
        List<Penalty> res = penaltyDAO.query(sql);
        if (res == null || res.isEmpty()) {
            throw new NullPointerException("No penalties found for rental with id " + id);
        }
        return res.get(0);
    }

    @Override
    public void createPenalty(Penalty entity) {
        penaltyDAO.create(entity);
    }

    @Override
    public void updatePenalty(Penalty entity) {
        penaltyDAO.update(entity);
    }

    @Override
    public void deletePenalty(Penalty entity) {
        penaltyDAO.delete(entity);
    }

    public void createRentingQueue(RentingQueue entity) {
        rentingQueueDAO.create(entity);
    }

    public void updateRentingQueue(RentingQueue entity) {
        rentingQueueDAO.update(entity);
    }

    public void deleteRentingQueue(RentingQueue entity) {
        rentingQueueDAO.delete(entity);
    }
    @Override
    public RentingQueue getRentingQueueById(int id) {
        return rentingQueueDAO.read(id);
    }

    @Override
    public List<RentingQueue> getAllRentingQueues() {
        return rentingQueueDAO.getAll();
    }

    @Override
    public List<RentingQueue> getRentingQueuesByBookId(int id) throws NullPointerException {
        String sql = "SELECT * FROM pap.renting_queue WHERE book_id = " + id + "ORDER BY date_to_return ASC";
        List<RentingQueue> list = rentingQueueDAO.query(sql);
        if (list.size() == 0) {
            throw new NullPointerException("Renting queues for book with id " + id + " not found");
        }
        return list;
    }

    @Override
    public List<RentingQueue> getRentingQueuesByUserId(int id) throws NullPointerException {
        String sql = "SELECT * FROM pap.renting_queue WHERE user_id = " + id;
        List<RentingQueue> list = rentingQueueDAO.query(sql);
        if (list.size() == 0) {
            throw new NullPointerException("Renting queues for user with id " + id + " not found");
        }
        return list;
    }
}
