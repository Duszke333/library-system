package pap.db.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ADDRESSES", schema = "pap", catalog = "postgres")
public class Address implements java.io.Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
        if (!Objects.equals(country, addresses.country)) return false;
        if (!Objects.equals(postalCode, addresses.postalCode)) return false;
        if (!Objects.equals(city, addresses.city)) return false;
        if (!Objects.equals(street, addresses.street)) return false;
        if (!Objects.equals(houseNumber, addresses.houseNumber))
            return false;
        return Objects.equals(flatNumber, addresses.flatNumber);
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
