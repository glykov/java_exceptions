/**
 * Реализуйте метод, который запрашивает у пользователя ввод дробного числа (типа float), и возвращает введенное значение. 
 * Ввод текста вместо числа не должно приводить к падению приложения, вместо этого, необходимо повторно запросить у пользователя ввод данных.
 */
import java.io.*;
import java.util.*;

public class GetFloat {
    public static float getFloat() {
        Scanner in = new Scanner(System.in);
        float value;

        System.out.print("Enter the floating point value: ");
        while (true) {
            try {
                value = in.nextFloat();
                break;
            } catch (InputMismatchException e) {
                in.nextLine();
                System.out.print("Invalid input. Please, try again: ");
            }
        }
        in.close();

        return value;
    }

    public static void main(String[] args) {
        float value = getFloat();
        System.out.println("You enetered " + value);
    }
}
