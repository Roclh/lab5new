package aclasses;

import classes.JsonParser;
import exceptionClasses.FindPersonException;
import interfaces.IQueueController;
import wrappers.Person;
import exceptionClasses.SavePeopleException;
import interfaces.IJsonParser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.PriorityQueue;
import java.util.Queue;

public abstract class AQueueController implements IQueueController {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private PriorityQueue<Person> queue;
    FindPersonException findPersonException;
    IJsonParser parser;


    //Конструктор загружает коллекцию из файла
    public AQueueController(JsonParser jsonParser) {
        this.parser = jsonParser;
        PriorityQueue<Person> buf = new PriorityQueue<>();
        try {
            boolean allOk = true;
            assert parser != null;
            long[] elementsId = new long[parser.getKolEl()];
            try {
                elementsId = parser.getId();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                allOk = false;
            }
            if (allOk) {
                for (int i = 0; i < parser.getKolEl(); i++) {
                    try {
                        buf.offer(parser.getSavedPerson(elementsId[i]));
                    } catch (IOException | SavePeopleException e) {
                        e.printStackTrace();
                    }
                }
                this.queue = buf;
                if (buf.size() > 0) System.out.println("Файл загружен в коллекцию");
                else System.out.println("Файл пустой");

            }
        }catch (FileNotFoundException e){
            System.out.println("Такого файла не существует");
        }
    }

    public Person getPersonById(long id) throws FindPersonException {
        Person person;
        Queue<Person> queue = new PriorityQueue<>(this.queue);
        try {
            while (((person = queue.poll()) != null)) {
                if (person.getId() == id) return person;
            }
        } catch (NullPointerException e) {
            throw findPersonException;
        }
        return null;
    }

    public void showQueue() {
        try {
            if (this.queue.size() > 0) {
                Queue<Person> queue = new PriorityQueue<>(this.queue);
                Person person;
                int queueSize = queue.size();
                for (int i = 0; i < queueSize; i++) {
                    person = queue.remove();
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
                }
            } else
                System.out.println("Очередь пустая");
        } catch (NullPointerException e) {
            System.out.println("Говна поешь");
        }

    }

    public Queue<Person> getQueue() {
        return queue;
    }
}
