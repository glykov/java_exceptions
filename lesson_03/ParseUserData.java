/**
 * Напишите приложение, которое будет запрашивать у пользователя следующие данные в произвольном порядке, разделенные пробелом:
 * Фамилия Имя Отчество датарождения номертелефона пол
 * Форматы данных:
 * фамилия, имя, отчество - строки
 * дата_рождения - строка формата dd.mm.yyyy
 * номер_телефона - целое беззнаковое число без форматирования
 * пол - символ латиницей f или m.
 * 1.
 * Приложение должно проверить введенные данные по количеству. 
 * Если количество не совпадает с требуемым, вернуть код ошибки, обработать его и 
 * показать пользователю сообщение, что он ввел меньше и больше данных, чем требуется.
 * 2.
 * Приложение должно попытаться распарсить полученные значения и выделить из них требуемые параметры. 
 * Если форматы данных не совпадают, нужно бросить исключение, соответствующее типу проблемы. 
 * Можно использовать встроенные типы java и создать свои. 
 * Исключение должно быть корректно обработано, пользователю выведено сообщение с информацией, что именно неверно.
 * 3.
 * Если всё введено и обработано верно, должен создаться файл с названием, равным фамилии, 
 * в него в одну строку должны записаться полученные данные, вида
 * <Фамилия><Имя><Отчество><датарождения> <номертелефона><пол>
 * Однофамильцы должны записаться в один и тот же файл, в отдельные строки.
 * 
 * Не забудьте закрыть соединение с файлом.
 * При возникновении проблемы с чтением-записью в файл, исключение должно быть корректно обработано, пользователь должен увидеть стектрейс ошибки.
 */
import java.util.*;
import java.io.*;
import java.time.*;
import java.util.regex.*;

public class ParseUserData {
    private static final int ITEMS_NUMBER = 6;

    // функция для получения данных от пользователя
    public static String getData() {
        Scanner in = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        
        System.out.print("Enter your first name: ");
        sb.append(in.nextLine() + " ");
        System.out.print("Enter your middle name: ");
        sb.append(in.nextLine() + " ");
        System.out.print("Enter your last name: ");
        sb.append(in.nextLine() + " ");
        System.out.print("Enter your date of birth (dd.mm.yyyy): ");
        sb.append(in.nextLine() + " ");
        System.out.print("Enter your phone number: ");
        sb.append(in.nextLine() + " ");
        System.out.print("Enter your gender: ");
        sb.append(in.nextLine());

        in.close();
        
        return sb.toString();
    }

    // 1. Функция для проверки количества переданных данных
    // с использованием кода ошибки
    public static int checkDataQuantity(String[] data) {
        return data.length - ITEMS_NUMBER;
    }

    // парсинг введенных данных с проверкой
    public static HashMap<String, String> parseData(String[] data) {
        HashMap<String, String> userData = new HashMap<>();
        int stringNumber = 0;
        for (String item : data) {
            if (item.length() == 1) {
                if (!item.equalsIgnoreCase("m") && !item.equalsIgnoreCase("f")) {
                    throw new RuntimeException("Wrong gender representation. It could be \"M\" of \"F\" only");
                }
                userData.put("Gender", item);
            } else if (item.charAt(0) >= '0' && item.charAt(0) <= '9') {
                if (item.indexOf(".") != -1) {
                    // проверка, правильная ли будет дата при приведении к типу LocalDate
                    LocalDate birthDay;
                    try {
                        String[] date = item.split("\\.");
                        birthDay = LocalDate.of(Integer.parseInt(date[2]), Integer.parseInt(date[1]), Integer.parseInt(date[0]));
                    } catch (NumberFormatException e) {
                        throw new RuntimeException("Wrong date representation");
                    }
                    userData.put("DateOfBirth", birthDay.toString());
                } else {
                    // проверка, правильно ли введен номер телефона
                    try {
                        int temp = Integer.parseInt(item);
                    } catch (NumberFormatException e) {
                        throw new RuntimeException("Wrong phone number reparesentation");
                    }
                    userData.put("PhoneNumber", item);
                }
            } else {
                Pattern pattern = Pattern.compile("[A-Z][a-z]+");
                Matcher matcher = pattern.matcher(item);
                boolean namePart = matcher.find();
                if (!namePart) {
                    String message = String.format("Wrong %s name", stringNumber == 0 ? "first" : (stringNumber == 1 ? "middle" : "last"));
                    throw new RuntimeException(message);
                }
                if (stringNumber == 0) {
                    userData.put("FirstName", item);
                } else if (stringNumber == 1) {
                    userData.put("MiddleName", item);
                } else {
                    userData.put("LastName", item);
                }
                stringNumber++;
            }
        }

        return userData;
    }

    // пишем данные пользователя в файл
    // <Фамилия><Имя><Отчество><датарождения> <номертелефона><пол>
    public static void saveUserToFile(HashMap<String, String> userData) throws IOException {
        PrintWriter writer = new PrintWriter(new FileOutputStream("data.txt", true), true);
        StringBuilder forPrinting = new StringBuilder();
        forPrinting.append(userData.get("LastName")).append(" ")
                .append(userData.get("FirstName")).append(" ")
                .append(userData.get("MiddleName")).append(" ")
                .append(userData.get("DateOfBirth")).append(" ")
                .append(userData.get("PhoneNumber")).append(" ")
                .append(userData.get("Gender"));
        writer.println(forPrinting.toString());
        writer.close();
    }

    public static void main(String[] args) {
        // String input = getData();
        String input = "Ivan Ivanovich Ivanov 12.12.1912 7777777 M";
        String[] data = input.split("\\s+");
        
        // обрабатываем код ошибки
        if (checkDataQuantity(data) > 0) {
            System.out.println("You entered too many data: " + (data.length - ITEMS_NUMBER) + " items more");
        } else if (checkDataQuantity(data) < 0) {
            System.out.println("You entered too few data: " + (ITEMS_NUMBER - data.length) + " items less");
        }

        // парсим переданные данные
        HashMap<String, String> userData;
        try {
            userData = parseData(data);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            userData = null;
        }

        if (userData != null) {
            for (var item : userData.entrySet()) {
                System.out.println(item.getKey() + ": " + item.getValue());
            }
            try {
                saveUserToFile(userData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("There are no data to show");
        }
    }
}
