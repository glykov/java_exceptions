import java.io.*;
import java.util.*;

public class Lesson01 {
    /**
     * 1. Реализуйте 3 метода, чтобы в каждом из них получить разные исключения
     */
    public static void nullPtr() {
        // обращение по нулевому указателю генерирует исключение NullPointerException
        String str = null;
        str.length();
    }

    public static void fileNotFound() throws IOException {
        // файл с данным именем отсутсвует, поэтому произойдет генерация исключения FileNotFoundException
        Scanner in = new Scanner(new FileInputStream(new File("abracadabra.txt")));
        while (in.hasNextLine()) {
            String line = in.nextLine();
            System.out.println(line);
        }
    }

    public static void numberFormat() {
        // невозможно преобразовать данную строку к int, генерация NumberFormatException
        int num = Integer.parseInt("there is no number here");
    }

    /**
     * 2. Реализуйте метод, принимающий в качестве аргументов два целочисленных массива, 
     * и возвращающий новый массив, каждый элемент которого равен разности элементов двух входящих массивов в той же ячейке. 
     * Если длины массивов не равны, необходимо как-то оповестить пользователя.
     */
    public static int[] sumOfArrays(int[] arr1, int[] arr2) {
        // на самом деле в данном случае нет необходимости пользоваться исключениями, т.к. размерность массивов проверяется
        // при выборе размера результирующего массива
        int resultLength = 0;
        String message = "";
        
        if (arr1.length > arr2.length) {
            resultLength = arr2.length;
            message = message + "Array 2 is shorter than array 1!"; 
        } else if (arr1.length < arr2.length) {
            resultLength = arr1.length;
            message = message + "Array 1 is shorter than array 2!";
        } else {
            resultLength = arr1.length;
        }

        if (!"".equals(message)) {
            System.out.println(message + " The result will be of its lenagth.");
        }

        int[] result = new int[resultLength];
        for (int i = 0; i < resultLength; i++) {
            result[i] = arr1[i] + arr2[i];
        }

        return result;
    }

    /**
     * 3. Реализуйте метод, принимающий в качестве аргументов два целочисленных массива, и возвращающий новый массив, 
     * каждый элемент которого равен частному элементов двух входящих массивов в той же ячейке. 
     * Если длины массивов не равны, необходимо как-то оповестить пользователя. 
     * Важно: При выполнении метода единственное исключение, которое пользователь может увидеть - RuntimeException, т.е. ваше.
     */
    public static int[] quotientOfArrays(int[] arr1, int[] arr2) {
        // на самом деле в данном случае нет необходимости пользоваться исключениями, т.к. размерность массивов проверяется
        // при выборе размера результирующего массива, однако по условию мы должны ими воспользоваться,
        // причем двумя => ArithmeticException и ArrayIndexOutOfBoundsException, перехватить их и сгенерировать свое
        // если я праильно понял задание, поэтому массив сперва собираем в ArrayList :-)
        List<Integer> temp = new ArrayList<>();

        try{
            for (int i = 0; i < arr1.length; i++) {
                temp.add(arr1[i] / arr2[i]);
            }
        } catch (ArithmeticException | IndexOutOfBoundsException e) {
            throw new RuntimeException(e.getMessage());
        }

        return temp.stream().mapToInt(Integer::intValue).toArray();
    }

    public static void main(String[] args) {
        int[] arr1 = {1, 2, 3, 4, 5};
        int[] arr2 = {1, 2, 0, 4, 5};
        int[] arr3 = {1, 2, 3};
        int[] result;

        try {
            result = quotientOfArrays(arr1, arr2);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        try {
            result = quotientOfArrays(arr1, arr3);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}