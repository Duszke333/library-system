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
@Getter
@Setter
@Table(name = "penalties", schema = "pap", catalog = "postgres")
public class Penalty implements java.io.Serializable{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "penalty_id")
    private int penaltyId;
    @Basic
    @Column(name = "user_id")
    private int userId;
    @Basic
    @Column(name = "rental_id")
    private int rentalId;
    @Basic
    @Column(name = "date_added")
    private Date dateAdded;
    @Basic
    @Column(name = "date_updated")
    private Date dateUpdated;
    @Basic
    @Column(name = "date_paid")
    private Date datePaid;
    @Basic
    @Column(name = "amount")
    private double amount;
    @Basic
    @Column(name = "cause")
    private String cause;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Penalty)) return false;
        Penalty penalty = (Penalty) o;
        return getPenaltyId() == penalty.getPenaltyId() && getUserId() == penalty.getUserId() && getRentalId() == penalty.getRentalId() && getAmount() == penalty.getAmount() && getDateAdded().equals(penalty.getDateAdded()) && getDatePaid().equals(penalty.getDatePaid()) && getCause().equals(penalty.getCause());
    }

    @Override
    public int hashCode() {
        int result = getPenaltyId();
        result = 31 * result + getUserId();
        result = 31 * result + getRentalId();
        result = 31 * result + getDateAdded().hashCode();
        result = 31 * result + getDatePaid().hashCode();
        result = 31 * result + (int) getAmount();
        result = 31 * result + getCause().hashCode();
        return result;
    }
}
