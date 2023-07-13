package HomeWork_3;
import java.io.*;
import java.nio.file.FileSystemException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/*
Напишите приложение, которое будет запрашивать у пользователя следующие данные в произвольном порядке, 
разделенные пробелом:
Фамилия Имя Отчество датарождения номертелефона пол

Форматы данных:
фамилия, имя, отчество - строки
датарождения - строка формата dd.mm.yyyy
номертелефона - целое беззнаковое число без форматирования
пол - символ латиницей f или m.

Приложение должно проверить введенные данные по количеству. 
Если количество не совпадает с требуемым, вернуть код ошибки, обработать его и показать пользователю сообщение, 
что он ввел меньше и больше данных, чем требуется.

Приложение должно попытаться распарсить полученные значения и выделить из них требуемые параметры. 
Если форматы данных не совпадают, нужно бросить исключение, соответствующее типу проблемы. 
Можно использовать встроенные типы java и создать свои. 
Исключение должно быть корректно обработано, пользователю выведено сообщение с информацией, что именно неверно.
*/

public class Main {
    public static void main(String[] args) throws IOException {
        try {
            makeRecord();
            System.out.println("success");
        }catch (FileSystemException e){
            System.out.println(e.getMessage());
        }
        catch (Exception e){
            System.out.println(e.getStackTrace());
        }

    }

    public static void makeRecord() throws Exception{
        System.out.println("Введите фамилию, имя, отчество, дату рождения (в формате dd.mm.yyyy), " +
        "номер телефона (число без разделителей) и пол(символ латиницей f или m), разделенные пробелом");

        String text;
        try(BufferedReader bf = new BufferedReader(new InputStreamReader(System.in))) {
            text = bf.readLine();
        }catch (IOException e){
            throw new Exception("Произошла ошибка при работе с консолью");
        }

        String[] array = text.split(" ");
        if (array.length != 6){
            throw new Exception("Введено неверное количество параметров");
        }

        String surname = array[0];
        String name = array[1];
        String patronymic = array[2];

        SimpleDateFormat format = new SimpleDateFormat("dd.mm.yyyy");
        Date birthdate;
        try {
            birthdate = format.parse(array[3]);
        }catch (ParseException e){
            throw new ParseException("Неверный формат даты рождения", e.getErrorOffset());
        }

        int phone;
        try {
            phone = Integer.parseInt(array[4]);
        }catch (NumberFormatException e){
            throw new NumberFormatException("Неверный формат телефона");
        }

        String sex = array[5];
        if (!sex.toLowerCase().equals("m") && !sex.toLowerCase().equals("f")){
            throw new RuntimeException("Неверно введен пол");
        }

        String fileName = "files.txt";
        File file = new File(fileName);
        try (FileWriter fileWriter = new FileWriter(file, true)){
            if (file.length() > 0){
                fileWriter.write('\n');
            }
            fileWriter.write(String.format("%s %s %s %s %s %s", surname, name, patronymic, birthdate, phone, sex));
        }catch (IOException e){
            throw new FileSystemException("Возникла ошибка при работе с файлом");
        }

    }
}

