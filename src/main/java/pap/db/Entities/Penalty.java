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
public class Penalty {
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
    @Column(name = "date_paid")
    private Date datePaid;
    @Basic
    @Column(name = "amount")
    private int amount;
    @Basic
    @Column(name = "cause")
    private String cause;
}
