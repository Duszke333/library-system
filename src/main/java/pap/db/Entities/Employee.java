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
@Table(name = "EMPLOYEES", schema = "pap", catalog = "postgres")
public class Employee implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id", nullable = false)
    private int employeeId;
    @Basic
    @Column(name = "password_hash", nullable = false, length = 256)
    private String passwordHash;
    @Basic
    @Column(name = "password_salt", nullable = false, length = 256)
    private String passwordSalt;
    @Basic
    @Column(name = "username", nullable = false, length = 128)
    private String username;
    @Basic
    @Column(name = "user_id", nullable = false)
    private int userID;
    @Basic
    @Column(name = "date_created", nullable = false)
    private Date dateCreated;
    @Basic
    @Column(name = "active", nullable = false)
    private boolean active;
    @Basic
    @Column(name = "role", nullable = false)
    private String role;
    @Basic
    @Column(name = "branch_id", nullable = false)
    private int branchId;




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (employeeId != employee.employeeId) return false;
        if (active != employee.active) return false;
        if (!Objects.equals(passwordHash, employee.passwordHash)) return false;
        if (!Objects.equals(passwordSalt, employee.passwordSalt)) return false;
        if (!Objects.equals(username, employee.username)) return false;
        if (userID != employee.userID) return false;
        if (!Objects.equals(dateCreated, employee.dateCreated)) return false;
        if (!Objects.equals(role, employee.role)) return false;
        if (branchId != employee.branchId) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = employeeId;

        result = 31 * result + (passwordHash != null ? passwordHash.hashCode() : 0);
        result = 31 * result + (passwordSalt != null ? passwordSalt.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + userID;
        result = 31 * result + (dateCreated != null ? dateCreated.hashCode() : 0);
        result = 31 * result + (active ? 1 : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + branchId;
        return result;
    }

}
