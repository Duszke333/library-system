
package pap;
import java.util.Scanner;

public class Pap {
    public static void main(String[] args) {
        System.out.println("Hello World");
        int x, y;
        Scanner input = new Scanner(System.in);
        System.out.print("Input first value: ");
        x = input.nextInt();
        System.out.print("Input second value: ");
        y = input.nextInt();
        System.out.println("x * y = " + x*y);
    }
}
