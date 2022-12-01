/**
 * В данном коде есть проблема с массивом intArray - 1) он не определен,
 * поэтому код даже не скомпилируется, 2) если его определить с размерностью
 * менее 9, то обращение к элементу с индексом 8 вызовет генерацию
 * исключения ArrayIndexOutOfBoundsException, которое не перехватывается в представленном коде
 * ArithmeticException (division by zero) обязательно будет сгенерировано, т.к. приведение 
 * к типу результата (double в данном случае) происходит после вычисления результата
 * если бы к типу double приводился один из операндов до выполонения деления, то исключение
 * не генерироввалось бы, а был бы получен результат типа Double.POSITIV_INFINITY или Double.NEGATIVE_INFINITY,
 * в зависимости от того положительное или отрицательное число в числителе
 *  
 * try {
   int d = 0;
   double catchedRes1 = intArray[8] / d;
   System.out.println("catchedRes1 = " + catchedRes1);
    } catch (ArithmeticException e) {
   System.out.println("Catching exception: " + e);
    }
 */

public class CodeCorrection01 {
    public static void main(String[] args) {
        int[] intArray = {1, 2, 3, 4, 5, 6, 7, 8}; // нет элемента с индексом 8
        try {
            int d = 0;
            double catchedResult = intArray[8] / d;
            System.out.println("catchedResult = " + catchedResult);
        } catch (ArithmeticException | ArrayIndexOutOfBoundsException e) {
            // т.к. доступ к массиву происходит раньше деления, исключение в данном случае будет IndexOutOfBounds
            // если, обратиться к действительному индексу массива, то перехвачено будет исключение ArithmeticException
            System.out.println("The error is " + e.getClass().getName() + " due to " + e.getMessage());
        }
    } 
}
