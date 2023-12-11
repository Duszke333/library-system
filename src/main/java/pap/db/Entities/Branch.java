package pap.db.Entities;

import jakarta.persistence.*;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "BRANCHES", schema = "view", catalog = "postgres")
public class Branch implements java.io.Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "branch_id", nullable = false)
    private int branchId;
    @Basic
    @Column(name = "address_id", nullable = false)
    private int addressId;
    @Basic
    @Column(name = "branch_name", nullable = false, length = 128)
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Branch)) return false;

        Branch branch = (Branch) o;

        if (branchId != branch.branchId) return false;
        if (addressId != branch.addressId) return false;
        return name.equals(branch.name);
    }

    @Override
    public int hashCode() {
        int result = branchId;
        result = 31 * result + addressId;
        result = 31 * result + name.hashCode();
        return result;
    }
}
