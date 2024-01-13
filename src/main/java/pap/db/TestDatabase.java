package pap.db;

import pap.db.Entities.BookGrade;
import pap.db.Entities.User;
import pap.db.Repository.BookRepository;
import pap.db.Repository.UserRepository;

import java.sql.Date;
import java.util.Scanner;



public class TestDatabase {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        UserRepository userRepository = new UserRepository();
        User user = userRepository.getByEmail("user@user.user");
        System.out.println(user.getEmail());
    }
}
