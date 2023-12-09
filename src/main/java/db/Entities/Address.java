package db.Entities;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ADDRESSES", schema = "pap", catalog = "postgres")
public class Address implements java.io.Serializable{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "address_id", nullable = false)
    private int addressId;
    @Basic
    @Column(name = "country", nullable = false, length = 64)
    private String country;
    @Basic
    @Column(name = "postal_code", nullable = false, length = 16)
    private String postalCode;
    @Basic
    @Column(name = "city", nullable = false, length = 64)
    private String city;
    @Basic
    @Column(name = "street", nullable = false, length = 64)
    private String street;
    @Basic
    @Column(name = "house_number", nullable = false, length = 16)
    private String houseNumber;
    @Basic
    @Column(name = "flat_number", nullable = true, length = 16)
    private String flatNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address addresses = (Address) o;

        if (addressId != addresses.addressId) return false;
        if (country != null ? !country.equals(addresses.country) : addresses.country != null) return false;
        if (postalCode != null ? !postalCode.equals(addresses.postalCode) : addresses.postalCode != null) return false;
        if (city != null ? !city.equals(addresses.city) : addresses.city != null) return false;
        if (street != null ? !street.equals(addresses.street) : addresses.street != null) return false;
        if (houseNumber != null ? !houseNumber.equals(addresses.houseNumber) : addresses.houseNumber != null)
            return false;
        if (flatNumber != null ? !flatNumber.equals(addresses.flatNumber) : addresses.flatNumber != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = addressId;
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (postalCode != null ? postalCode.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (houseNumber != null ? houseNumber.hashCode() : 0);
        result = 31 * result + (flatNumber != null ? flatNumber.hashCode() : 0);
        return result;
    }
}
