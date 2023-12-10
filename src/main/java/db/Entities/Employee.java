package db.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

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
        if (addressId != employee.addressId) return false;
        if (active != employee.active) return false;
        if (passwordHash != null ? !passwordHash.equals(employee.passwordHash) : employee.passwordHash != null) return false;
        if (passwordSalt != null ? !passwordSalt.equals(employee.passwordSalt) : employee.passwordSalt != null) return false;
        if (firstName != null ? !firstName.equals(employee.firstName) : employee.firstName != null) return false;
        if (lastName != null ? !lastName.equals(employee.lastName) : employee.lastName != null) return false;
        if (email != null ? !email.equals(employee.email) : employee.email != null) return false;
        if (dateCreated != null ? !dateCreated.equals(employee.dateCreated) : employee.dateCreated != null) return false;
        if (role != null ? !role.equals(employee.role) : employee.role != null) return false;
        if (branchId != employee.branchId) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = employeeId;

        result = 31 * result + (passwordHash != null ? passwordHash.hashCode() : 0);
        result = 31 * result + (passwordSalt != null ? passwordSalt.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + addressId;
        result = 31 * result + (dateCreated != null ? dateCreated.hashCode() : 0);
        result = 31 * result + (active ? 1 : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + branchId;
        return result;
    }

}
