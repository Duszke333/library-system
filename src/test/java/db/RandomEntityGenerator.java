package db;

import pap.db.Entities.*;

import java.sql.Date;

public class RandomEntityGenerator {
    public static Book generateBook() {
        Book book = new Book();
        book.setTitle(HelperMethods.getRandomString(10));
        book.setIsbn(HelperMethods.getRandomString(10));
        book.setAuthor(HelperMethods.getRandomString(10));
        book.setGenre(HelperMethods.getRandomString(10));
        book.setDateAdded(new Date(HelperMethods.getRandomDate().getTime()));
        book.setPublicationYear(HelperMethods.getRandomInt(0, 1000));
        book.setPublisher(HelperMethods.getRandomString(10));
        book.setPageCount(HelperMethods.getRandomInt(0, 1000));
        book.setCover(HelperMethods.getRandomString(10));
        book.setLanguage(HelperMethods.getRandomString(10));
        book.setStatus(HelperMethods.getRandomString(10));
        book.setDescription(HelperMethods.getRandomString(10));
        return book;
    }

    public static Address generateAddress() {
        Address address = new Address();
        address.setStreet(HelperMethods.getRandomString(10));
        address.setFlatNumber(HelperMethods.getRandomString(10));
        address.setHouseNumber(HelperMethods.getRandomString(10));
        address.setCity(HelperMethods.getRandomString(10));
        address.setCountry(HelperMethods.getRandomString(10));
        address.setPostalCode(HelperMethods.getRandomString(10));
        return address;
    }

    public static User generateUser(Address address) {
        User user = new User();
        user.setFirstName(HelperMethods.getRandomString(10));
        user.setLastName(HelperMethods.getRandomString(10));
        user.setEmail(HelperMethods.getRandomEmail());
        user.setPasswordHash(HelperMethods.getRandomString(10));
        user.setPasswordSalt(HelperMethods.getRandomString(10));
        user.setActive(true);
        user.setAddressId(address.getAddressId());
        user.setHasUnpaidPenalty(false);
        return user;
    }

    public static Branch generateBranch(Address address) {
        Branch branch = new Branch();
        branch.setAddressId(address.getAddressId());
        branch.setName(HelperMethods.getRandomString(10));
        return branch;
    }

    public static Employee generateEmployee(User user, Branch branch) {
        Employee employee = new Employee();
        employee.setBranchId(branch.getBranchId());
        employee.setUsername(HelperMethods.getRandomString(10));
        employee.setPasswordHash(HelperMethods.getRandomString(10));
        employee.setPasswordSalt(HelperMethods.getRandomString(10));
        employee.setActive(true);
        employee.setUserID(user.getAccountId());
        employee.setDateCreated(new Date(HelperMethods.getRandomDate().getTime()));
        employee.setRole(HelperMethods.getRandomString(10));
        return employee;
    }
}
