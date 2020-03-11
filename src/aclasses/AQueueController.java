package aclasses;

import classes.subclasses.JsonParser;
import exceptionClasses.FindPersonException;
import interfaces.IQueueController;
import wrappers.Person;
import exceptionClasses.SavePeopleException;
import interfaces.IJsonParser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Данный класс отвечает за работу с элементами коллекции, а именно её хранением, загрузкой из файла, выводом и получением
 * конкрентного элемента по его id
 *
 */

public abstract class AQueueController implements IQueueController {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private PriorityQueue<Person> queue;
    FindPersonException findPersonException;
    IJsonParser parser;


    /**
     * Конструктор загружает коллекцию из файла
     * @param jsonParser
     * Для корректной работы программы мы вносим в конструктор конкретный парсер
     */
    public AQueueController(JsonParser jsonParser) {
        this.setParser(jsonParser);
        PriorityQueue<Person> buf = new PriorityQueue<>();
        try {
            int kolEl = this.getParser().getKolEl();
            boolean allOk = true;
            long[] elementsId = new long[kolEl];
            try {
                elementsId = this.getParser().getId();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                allOk = false;
            }
            if (allOk) {
                for (int i = 0; i < kolEl; i++) {
                    try {
                        buf.offer(this.getParser().getSavedPerson(elementsId[i]));
                    } catch (IOException | SavePeopleException e) {
                        e.printStackTrace();
                    }
                }
                this.setQueue(buf);
                if (this.getQueue().size() > 0) System.out.println("Файл загружен в коллекцию");
                else System.out.println("Файл пустой");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Такого файла не существует");
        }
    }

    /**
     * Метод возвращает объект из коллекции с заданным id
     * @param id
     * Это id элемента коллекци, по которому ищется нужный нам объект
     * @return
     * Метод возвращает объект класса Person с нужным id
     */
    public Person getPersonById(long id){
        try {
            return this.getQueue().stream().filter(person -> person.getId() == id).findFirst().get();
        }catch (NoSuchElementException e){
            return null;
        }
    }

    /**
     * Метод выводит на экран все элементы коллекции в сжатом формате json
     */
    public void showQueue() {
        this.queue.forEach(person -> {
                    System.out.print("{\"name\":\"" + person.getName() + "\";");
                    System.out.print("\"id\":\"" + person.getId() + "\";");
                    System.out.print("\"coordinates\"{\"X\":\"" + person.getCoordinates().getX().toString() + "\";\"Y\":\"" +
                            person.getCoordinates().getY() + "\"};");
                    System.out.print("\"creationDate\":\"" + dtf.format(person.getCreationDate()) + "\";\n    ");
                    System.out.print("\"height\":\"" + person.getHeight().toString() + "\";");
                    System.out.print("\"eyeColor\":\"" + person.getEyeColor().toString() + "\";");
                    System.out.print("\"hairColor\":\"" + person.getHairColor().toString() + "\";");
                    System.out.print("\"nationality\":\"" + person.getNationality().toString() + "\";\n    ");
                    System.out.print("\"location\"{\"X\":" + person.getLocation().getX().toString() + "\";\"Y\":" +
                            person.getLocation().getY().toString() + "\";\"Z\":\"" +
                            person.getLocation().getZ().toString() + "\"}\n");
                });
        }


    public void setQueue(PriorityQueue<Person> queue) {
        this.queue = queue;
    }

    public IJsonParser getParser() {
        return parser;
    }

    public void setParser(IJsonParser parser) {
        this.parser = parser;
    }

    public Queue<Person> getQueue() {
        return queue;
    }
}
