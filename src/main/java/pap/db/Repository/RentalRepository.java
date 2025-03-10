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
    public List<BookRental> getRentalsByBookId(int id) {
        String sql = "SELECT * FROM pap.book_rentals WHERE book_id = " + id;
        return bookRentalDAO.query(sql);
    }
    @Override
    public List<BookRental> getRentalsByUserId(int id) {
        String sql = "SELECT * FROM pap.book_rentals WHERE user_id = " + id;
        return bookRentalDAO.query(sql);
    }

    public List<BookRental> getAllExceededRentals() {
        String sql = "SELECT * FROM pap.book_rentals WHERE date_returned IS NULL AND date_to_return < NOW()";
        return bookRentalDAO.query(sql);
    }

    public BookRental getCurrentBookRental(int id) {
        String sql = "SELECT * FROM pap.book_rentals WHERE book_id = " + id + " AND date_returned IS NULL";
        List<BookRental> res = bookRentalDAO.query(sql);
        if (res == null || res.isEmpty()) {
            return null;
        }
        return res.get(0);
    }

    public List<BookRental> getUserCurrentRentals(int id) {
        String sql = "SELECT * FROM pap.book_rentals WHERE user_id = " + id + " AND date_returned IS NULL";
        return bookRentalDAO.query(sql);
    }

    public BookRental getLastBookRental(int id) {
        String sql = "SELECT * FROM pap.book_rentals WHERE book_id = " + id + " ORDER BY date_rented DESC LIMIT 1";
        List<BookRental> res = bookRentalDAO.query(sql);
        if (res == null || res.isEmpty()) {
            return null;
        }
        return res.get(0);
    }

    @Override
    public boolean isRentedByUser(int userId, int bookId){
        String sql = "SELECT * FROM pap.book_rentals WHERE user_id = " + userId + " AND book_id = " + bookId + " AND date_returned is null";
        return !bookRentalDAO.query(sql).isEmpty();
    }
    @Override
    public Penalty getPenaltyById(int id) {
        return penaltyDAO.read(id);
    }

    @Override
    public List<Penalty> getAllPenalties() {
        return penaltyDAO.getAll();
    }

    @Override
    public List<Penalty> getPenaltiesByUserId(int id) {
        String sql = "SELECT * FROM pap.penalties WHERE user_id = " + id;
        return penaltyDAO.query(sql);
    }

    public List<Penalty> getUnpaidUserPenalties(int id) {
        String sql = "SELECT * FROM pap.penalties WHERE user_id = " + id + " AND date_paid IS NULL";
        return penaltyDAO.query(sql);
    }

    @Override
    public List<Penalty> getPenaltiesByBookId(int id) {
        String sql = "SELECT * FROM pap.penalties WHERE book_id = " + id;
        return penaltyDAO.query(sql);
    }

    public Penalty getPenaltyByRentalId(int id) {
        String sql = "SELECT * FROM pap.penalties WHERE rental_id = " + id + " AND date_paid IS NULL";
        List<Penalty> res = penaltyDAO.query(sql);
        if (res == null || res.isEmpty()) {
            return null;
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
    public List<RentingQueue> getRentingQueuesByBookId(int id) {
        String sql = "SELECT * FROM pap.renting_queue WHERE book_id = " + id + "ORDER BY date_to_return ASC";
        return rentingQueueDAO.query(sql);
    }

    @Override
    public List<RentingQueue> getRentingQueuesByUserId(int id) {
        String sql = "SELECT * FROM pap.renting_queue WHERE user_id = " + id;
        return rentingQueueDAO.query(sql);
    }
}
