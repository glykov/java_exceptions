/**
 * Разработайте программу, которая выбросит Exception, когда пользователь вводит пустую строку. 
 * Пользователю должно показаться сообщение, что пустые строки вводить нельзя.
 */
 import java.util.*;

 public class NoEmptyStrings {
    private static class EmptyStringException extends RuntimeException {
        EmptyStringException() {
            this("What are you doing? Empty strings are not allowed!");
        }

        EmptyStringException(String message) {
            super(message);
        }
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        try {
            String input = in.nextLine();
            if ("".equals(input)) {
                throw new EmptyStringException();
            }
            System.out.println("Your input is \"" + input + "\"");
        } catch (EmptyStringException e) {
            System.out.println(e.getMessage());
        }
    }
}
