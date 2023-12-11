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
@Table(name = "USERS", schema = "pap", catalog = "postgres")
public class User implements java.io.Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id", nullable = false)
    private int accountId;
    @Basic
    @Column(name = "password_hash", nullable = false, length = 256)
    private String passwordHash;
    @Basic
    @Column(name = "password_salt", nullable = false, length = 256)
    private String passwordSalt;
    @Basic
    @Column(name = "first_name", nullable = false, length = 128)
    private String firstName;
    @Basic
    @Column(name = "last_name", nullable = false, length = 128)
    private String lastName;
    @Basic
    @Column(name = "email", nullable = false, length = 128)
    private String email;
    @Basic
    @Column(name = "address_id", nullable = false)
    private int addressId;
    @Basic
    @Column(name = "date_created", nullable = false)
    private Date dateCreated;
    @Basic
    @Column(name = "active", nullable = false)
    private boolean active;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (accountId != user.accountId) return false;
        if (addressId != user.addressId) return false;
        if (active != user.active) return false;
        if (!Objects.equals(passwordHash, user.passwordHash)) return false;
        if (!Objects.equals(passwordSalt, user.passwordSalt)) return false;
        if (!Objects.equals(firstName, user.firstName)) return false;
        if (!Objects.equals(lastName, user.lastName)) return false;
        if (!Objects.equals(email, user.email)) return false;
        return Objects.equals(dateCreated, user.dateCreated);
    }

    @Override
    public int hashCode() {
        int result = accountId;
        result = 31 * result + (passwordHash != null ? passwordHash.hashCode() : 0);
        result = 31 * result + (passwordSalt != null ? passwordSalt.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + addressId;
        result = 31 * result + (dateCreated != null ? dateCreated.hashCode() : 0);
        result = 31 * result + (active ? 1 : 0);
        return result;
    }
}
