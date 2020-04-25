package aclasses;

import wrappers.Coordinates;
import wrappers.Location;
import wrappers.Person;
import enums.Country;
import enums.EyeColor;
import enums.HairColor;
import exceptionClasses.SavePeopleException;
import interfaces.IJsonParser;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * @author Roclh
 * @version 1.01v
 *
 * Парсер из объекта класса Person в формат json
 */

public abstract class AJsonParser implements IJsonParser {
    private File file = new File("C:\\Users\\Nikit\\Desktop\\ITMO\\Программирование\\lab5\\files\\person_info.json");
    private SavePeopleException SaveError;

    /**
     * Метод указывает парсеру с каким файлом будет работать программа
     * @param path
     * Параметр содержит в себе путь к файлу
     * @return
     * Возвращает true, если файл существует и у пользователя есть права для чтения файла
     */
    public boolean setFile(String path){
        this.file = new File(path);
        if(!this.file.exists()){System.out.println("Такого файла не существует"); return false;}
        if(!this.file.canRead()){System.out.println("Недостаточно прав для чтения файла"); return false;}
        return true;
    }

    /**
     * Метод, возвращающий информацию о файле, с которым работает парсер
     * @return
     * Возвращает true, если файл доступен для записи
     */
    public boolean isWritable(){
        return this.file.canWrite();
    }

    /**
     * Метод, который отчищает файл сохранения путем записи в файл пустой строки поверх всей информации
     * @throws IOException
     * Может выбрасывать исключение в случае неверного файла для записи
     */
    public void resetSaveFile(){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write("".getBytes());
        } catch (IOException e){
            System.out.println("Ошибка отчистки файла");
        }
    }

    /**
     * Метод, осуществляющий запись объекта типа Person в файл в формате json
     * Каждый параметр объекта записывается в файл отдельной строкой, соблюдая формат json
     *
     * @param person
     * Объект класса Person, чьи параметры мы записываем в файл
     * @throws IOException
     * Может выбрасывать исключение в случае неправильного файла
     *
     */
    public void savePerson(Person person) throws IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        FileOutputStream fileOutputStream = new FileOutputStream(file, true);
        fileOutputStream.write("{\r\n".getBytes());
        fileOutputStream.write(("   \"id\": " + person.getId() + ",\r\n").getBytes());
        fileOutputStream.write(("   \"name\": \"" + person.getName() + "\",\r\n").getBytes());
        fileOutputStream.write(("   \"coordinates\": {\r\n        \"X\": " + person.getCoordinates().getX().toString() + ",\r\n        \"Y\": " + person.getCoordinates().getY() + "\r\n    },\r\n").getBytes());
        fileOutputStream.write(("   \"creationDate\": \"" + dtf.format(person.getCreationDate()) + "\",\r\n").getBytes());
        fileOutputStream.write(("   \"height\": " + person.getHeight().toString() + ",\r\n").getBytes());
        fileOutputStream.write(("   \"eyeColor\": \"" + person.getEyeColor().toString() + "\",\r\n").getBytes());
        fileOutputStream.write(("   \"hairColor\": \"" + person.getHairColor().toString() + "\",\r\n").getBytes());
        fileOutputStream.write(("   \"nationality\": \"" + person.getNationality().toString() + "\",\r\n").getBytes());
        fileOutputStream.write(("   \"location\": {\r\n        \"X\": " + person.getLocation().getX().toString() + ",\r\n        \"Y\": " + person.getLocation().getY().toString() + ",\r\n        \"Z\": " + person.getLocation().getZ().toString() + "\r\n    }\r\n").getBytes());
        fileOutputStream.write("}\r\n".getBytes());
    }

    //Набор массива, состоящего из Id сохраненных в файле людей
    public long[] getId() throws FileNotFoundException {
        FileReader fr = new FileReader(file);
        long[] idArray = new long[getKolEl()];
        Scanner sc = new Scanner(fr);
        String buf = "";
        int i = 0;
        while (sc.hasNextLine()) {//Пока следующая строчка существует, проверяется наличие в строке id. После чего,
            buf = sc.nextLine();//следуя из формата, достается текст и конвертируется в тип long
            if (buf.contains("\"id\":")) {
                buf = buf.substring(0, buf.lastIndexOf(","));
                buf = buf.substring(buf.indexOf("\": ")+3);
                idArray[i] = Long.parseLong(buf);
                i++;
            }
        }
        return idArray;
    }


    //Парсер из файла в класс Person
    //Следуя из формата сохранения, построчно берутся данные для создания объекта Person
    public Person getSavedPerson(long id) throws IOException, SavePeopleException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        Coordinates coordinates = new Coordinates((long) 1, 1);
        Location location = new Location(1, 1f, 1f);
        Person person = new Person();
        try {
            System.out.println("Найден объект с Id " + id);
            FileReader fr = new FileReader(file);
            Scanner sc = new Scanner(fr);
            String buf = "";
            boolean idEnded = false;
            while (!idEnded) {
                buf = sc.nextLine();
                if (buf.contains(Long.toString(id))) {
                    person.setId(id);
                    buf = sc.nextLine();
                    person.setName(buf.substring(buf.indexOf("\"name\": \"")+9,buf.lastIndexOf("\",")));
                    buf = sc.nextLine();
                    buf = sc.nextLine();
                    coordinates.setX(Long.valueOf(buf.substring(buf.indexOf("\"X\": ")+5,buf.lastIndexOf(","))));
                    buf = sc.nextLine();
                    coordinates.setY(Float.parseFloat(buf.substring(buf.indexOf("\"Y\": ")+5)));
                    person.setCoordinates(coordinates);
                    buf = sc.nextLine();
                    buf = sc.nextLine();
                    person.setCreationDate(LocalDateTime.parse(buf.substring(buf.indexOf("\"creationDate\": \"") + 17, buf.lastIndexOf("\",")), dtf));
                    buf = sc.nextLine();
                    person.setHeight(Float.parseFloat(buf.substring(buf.indexOf("\"height\": ") + 10,buf.lastIndexOf(","))));
                    buf = sc.nextLine();
                    person.setEyeColor(EyeColor.valueOf(buf.substring(buf.indexOf("\"eyeColor\": \"") + 13, buf.lastIndexOf("\","))));
                    buf = sc.nextLine();
                    person.setHairColor(HairColor.valueOf(buf.substring(buf.indexOf("\"hairColor\": \"") + 14, buf.lastIndexOf("\","))));
                    buf = sc.nextLine();
                    person.setNationality(Country.valueOf(buf.substring(buf.indexOf("\"nationality\": \"") + 16, buf.lastIndexOf("\","))));
                    buf = sc.nextLine();
                    buf = sc.nextLine();
                    location.setX(Integer.valueOf(buf.substring(buf.indexOf("\"X\": ")+5,buf.lastIndexOf(","))));
                    buf = sc.nextLine();
                    location.setY(Float.valueOf(buf.substring(buf.indexOf("\"Y\": ")+5,buf.lastIndexOf(","))));
                    buf = sc.nextLine();
                    location.setZ(Float.valueOf(buf.substring(buf.indexOf("\"Z\": ")+5)));
                    person.setLocation(location);
                    idEnded = true;
                    System.out.println("Данные из файла взяты");
                }

            }
            fr.close();
            return person;
        } catch (FileNotFoundException e){
            System.out.println("Такого файла не существует");
            throw SaveError;
        }
    }

    //Счетчик экземпляров в коллекции в файле
    //Подсчитывается количество строк в файле, в которых есть подстрока "id":
    public int getKolEl() throws FileNotFoundException {
        FileReader fr;
        fr = new FileReader(file);
        Scanner sc = new Scanner(fr);
        String buf = "";
        int i = 0;
        while (sc.hasNextLine()) {
            buf = sc.nextLine();
            if (buf.contains("\"id\":")) {
                i++;
            }
        }

        return i;
    }


    //Стандартный конструктор
    protected AJsonParser() throws FileNotFoundException {
    }
}
