/**
 * public static void main(String[] args) throws Exception {
   try {
       int a = 90;
       int b = 3;
       System.out.println(a / b);
       printSum(23, 234);
       int[] abc = { 1, 2 };
       abc[3] = 9;
   } catch (Throwable ex) {
       System.out.println("Что-то пошло не так...");
   } catch (NullPointerException ex) {
       System.out.println("Указатель не может указывать на null!");
   } catch (IndexOutOfBoundsException ex) {
       System.out.println("Массив выходит за пределы своего размера!");
   }
}
public static void printSum(Integer a, Integer b) throws FileNotFoundException {
   System.out.println(a + b);
}
 * 1) при вызове функции printSum произойдет ошибка компиляции, т.к. FileNotFoundException
 * является производным классом от IOException, который определен в пакете java.io, соответсвенно
 * данный пакет необходимо имопртировать или убрать данную декларацию (см. 5)
 * 2) имеется ошибка в порядке обработчиков исключений - самый "широкий" обработчик 
 * (в данном случае перехват родительского класса всех исключений и ошибок Throwable)
 * должен обрабатываться последним, иначе, более специализированные исключения будут перехвачены этим обработчиком
 * 3) учитывая наличие обработчика Throwable внутри функции main она не может (в данном случае)
 * пробрасывать исключения вызывающему коду -- все исключения, возникшие в main будут обработаны в ней же
 * Кроме того, учитывая, что мы не можем обработать ошибки типа Error, не имеет смысла использовать столь "широкий"
 * обработчик, достаточно использовать обработчик для класса Exception 
 * 4) в данном фрагменте нет кода, способного сгенерировать NullPointerException -- это лишний обработчик
 * Целесообразно будет его заменить на обработчик ArithmeticException
 * 5) в функции printSum нет кода, способного сгенерировать FileNotFoundException -- не имеет смысла декларировать,
 * что функция может его сгенерировать, т.к. внутри функции не осуществляется доступ к файлам  
 * 
 */

public class CodeCorrection02 {
    public static void main(String[] args) {
        try {
            int a = 90;
            int b = 3;
            System.out.println(a / b);
            printSum(23, 234);
            int[] abc = { 1, 2 };
            abc[3] = 9;
        }  catch (ArithmeticException ex) {
            System.out.println("Делить на нуль нельзя!");
        } catch (IndexOutOfBoundsException ex) {
            System.out.println("Массив выходит за пределы своего размера!");
        } catch (Exception ex) {
            System.out.println("Что-то пошло не так...");
        }
    }
    public static void printSum(Integer a, Integer b) {
        System.out.println(a + b);
    } 
}
