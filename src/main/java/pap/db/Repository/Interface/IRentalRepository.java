package pap.db.Repository.Interface;

import pap.db.Entities.BookRental;
import pap.db.Entities.Penalty;
import pap.db.Entities.RentingQueue;

import java.util.List;

public interface IRentalRepository extends IRepository<BookRental> {
    List<BookRental> getRentalsByUserId(int id);
    Penalty getPenaltyById(int id);
    List<Penalty> getAllPenalties();
    List<Penalty> getPenaltiesByUserId(int id);
    List<Penalty> getPenaltiesByBookId(int id);
    void createPenalty(Penalty entity);
    void updatePenalty(Penalty entity);
    void deletePenalty(Penalty entity);
    RentingQueue getRentingQueueById(int id);
    List<RentingQueue> getAllRentingQueues();
    List<RentingQueue> getRentingQueuesByBookId(int id);
    List<RentingQueue> getRentingQueuesByUserId(int id);
}
