package classes;

import classes.subclasses.CommandTranslator;
import classes.subclasses.JsonParser;
import classes.subclasses.QueueController;
import classes.subclasses.Terminal;
import enums.Country;
import enums.EyeColor;
import enums.HairColor;
import exceptionClasses.FindPersonException;
import exceptionClasses.SavePeopleException;
import interfaces.ICommandTranslator;
import interfaces.ITerminal;
import wrappers.Command;
import wrappers.History;
import wrappers.Person;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

/**
"Панель управления" программой. Тут находятся методы, исполняющие все пользовательские комманды
Названия методов совпадают с коммандами
 */


public class Controller {

    private String pathname;
    private SavePeopleException e;
    private ITerminal terminal = new Terminal();
    private ICommandTranslator commandTranslator = new CommandTranslator();
    private JsonParser jsonParser = new JsonParser();


    /**
     * Контроллер будет просить ввести путь к файлу до тех пор, пока не будет написан верный путь
     * @throws FileNotFoundException
     * Может выбрасывать исключения
     */
    public Controller() throws FileNotFoundException {
            String buf = terminal.readLine("Введите путь к файлу");
            this.jsonParser.setFile(buf);
    }

    /**
     * Метод help выводит общую информацию о коммандах, которые реализованны в программе
     */
    public void help() {
        System.out.println("===================================================================================");
        System.out.println("\"help command\" - вывести подробную справку о конкретной комманде");
        System.out.println("\"info\" - вывести информацию о коллекции");
        System.out.println("\"show\" - вывести все элементы коллекции в строковом представлении");
        System.out.println("\"add {element}\" - добавить новый элемент в коллекцию");
        System.out.println("\"update_id {element}\" - изменить элемент коллекции, чей id равен заданному");
        System.out.println("\"remove_by_id id\" - удалить элемент из коллекции, чей id равен заданному");
        System.out.println("\"clear\" - отчистить коллекцию");
        System.out.println("\"save\" - сохранить коллекцию в файл");
        System.out.println("\"execute_script file_name\" - считать и исполнить скрипт из указанного файла.");
        System.out.println("\"exit\" - закрыть программу");
        System.out.println("\"add_if_min {element}\" - добавить новый элемент в коллекцию, если его значение\nменьше, чем у наименьшего элемента этой коллекции");
        System.out.println("\"remove_lower {element}\" - удалить все элементы, меньшие, чем заданный");
        System.out.println("\"history\" - вывести последние 6 комманд");
        System.out.println("\"count_by_hair_color hairColor\" - вывести количество элементов с данным цветом волос");
        System.out.println("\"filter_starts_with_name name\" - вывести элементы, значения поля name которых \nначинается с заданной подстроки");
        System.out.println("\"print_unique_height height\" - вывести все уникальнын значения поля height");
        System.out.println("===================================================================================");
    }

    /**
     * Метод help с аргументом выводит подробную информацию о конкретной команде, как ей пользоваться
     * и как она должна выглядеть
     * @param commandName
     * В качестве параметра поступает команда, любая из метода help
     */
    public void help(String commandName) {
        switch (commandName) {
            case "info":
                System.out.println("=================================================================");
                System.out.println("Данная команда выводит общую информацию о коллекции, а именно:");
                System.out.println("Объекты, хранимые в коллекции");
                System.out.println("Тип коллекции");
                System.out.println("Количество элементов коллекции");
                System.out.println("Имя каждого элемента коллекции + его id");
                System.out.println("=================================================================");
                break;
            case "show":
                System.out.println("=================================================================");
                System.out.println("Выводит информацию о каждом объекте, хранимом в коллекции в формате Json");
                System.out.println("=================================================================");
                break;
            case "add":
                System.out.println("=================================================================");
                System.out.println("Имеет два варианта работы: ");
                System.out.println("1. Вариант работы без аргумента: ");
                System.out.println("Программа будет построчно просить ввести каждый элемент обхекта Person");
                System.out.println("2. Вариант с аргументом строки, записанном в формате json");
                System.out.println("Программа будет ожидать от пользователя ввода вида ");
                System.out.println("add {\"name\":\"Степан\";\"coordinates\":{\"X\":\"123\";\"Y\":\"3234\";....;}");
                System.out.println("Вводится в данном виде могут быть любые параметры объекта Person в любом порядке");
                System.out.println("Любые параметры, которые не будут записаны, будут созданны автоматически случайным образом");
                System.out.println("=================================================================");
                System.out.println("Некоторые параметры имеют ограниченное количество возможных вариантов ввода");
                System.out.println("Параметр hairColor может принимать значения:");
                System.out.println("GREEN, BLACK, PINK, YELLOW, ORANGE, WHITE");
                System.out.println("Параметр eyeColor может принимать значения:");
                System.out.println("RED, BLUE, YELLOW");
                System.out.println("Параметр nationality может принимать значения");
                System.out.println("INDIA, VATICAN, NORTH_AMERICA, JAPAN");
                System.out.println("Параметр height не может быть отрицательным");
                System.out.println("=================================================================");
                break;
            case "update":
                System.out.println("=================================================================");
                System.out.println("Команда ожидает на вход два аргумента");
                System.out.println("Первый аргумент хранит в себе id элемента");
                System.out.println("Второй аргумент хранит параметры, которые нужно изменить");
                System.out.println("     Пример работающией строки:");
                System.out.println("update 123456 {\"name\":\"Степан\";}");
                System.out.println("=================================================================");
                break;
            case "remove_by_id":
                System.out.println("=================================================================");
                System.out.println("Команда ожидает на вход один аргумент");
                System.out.println("Аргумент должен хранить в себе id существующего объекта коллекции");
                System.out.println("Для удобства удаления рекомендуется использовать в комбинации с\nкомандой info");
                System.out.println("=================================================================");
                break;
            case "clear":
                System.out.println("=================================================================");
                System.out.println("Удаляет все элементы коллекции");
                System.out.println("=================================================================");
                break;
            case "save":
                System.out.println("=================================================================");
                System.out.println("Сохраняет все элементы коллекции в читаемый файл в формате json");
                System.out.println("=================================================================");
                break;
            case "execute_script":
                System.out.println("=================================================================");
                System.out.println("Команда ожидает на вход один аргумент");
                System.out.println("Аргумент должен хранить в себе полный или относительный путь до файла скрипта");
                System.out.println("Пользователь должен иметь нужные права доступа для чтения файла");
                System.out.println("Для удобства, несколько примеров скриптов находятся в папке files/");
                System.out.println("Скрипт может исполнять другие скрипты, но для избежания рекурсии ");
                System.out.println("если скрипт исполняет сам себя, или скрипт исполняет другой скрипт, в ");
                System.out.println("который вложен этот скрипт, то исполнение команды будет проигнорированно");
                System.out.println("=================================================================");
                break;
            case "exit":
                System.out.println("=================================================================");
                System.out.println("Команда завершает работу программы");
                System.out.println("=================================================================");
                break;
            case "add_if_min":
                System.out.println("=================================================================");
                System.out.println("Команда добавляет элемент в коллекцию, если он меньше остальных элементов");
                System.out.println("Команда ожидает на вход параметр в формате json, см \"help add\"");
                System.out.println("=================================================================");
                break;
            case "remove_lower":
                System.out.println("=================================================================");
                System.out.println("Команда удаляет все элементы коллекции, если они меньше, чем заданный");
                System.out.println("Команда ождиает на вхож параметр в формате json, см \"help add\"");
                System.out.println("=================================================================");
                break;
            case "history":
                System.out.println("=================================================================");
                System.out.println("Команда возращает последние 6 команд, которые пользователь пытался ввести");
                System.out.println("=================================================================");
                break;
            case "count_by_hair_color":
                System.out.println("=================================================================");
                System.out.println("Команда возвращает число объектов, у которых параметр hairColor равен заданному");
                System.out.println("Возможные значения hairColor:");
                System.out.println("GREEN, BLACK, PINK, YELLOW, ORANGE, WHITE");
                System.out.println("=================================================================");
                break;
            case "filter_starts_with_name":
                System.out.println("=================================================================");
                System.out.println("Команда ожидает на вход подстроку, которую программа будет искать в начале");
                System.out.println("имен объектов коллекции");
                System.out.println("=================================================================");
                break;
            case "print_unique_height":
                System.out.println("=================================================================");
                System.out.println("Команда ожидает на вход положительное число");
                System.out.println("Программа вернет все имена обънетоа, у которых параметр height уникален");
                System.out.println("=================================================================");
                break;
            case "helpImPetr":
                System.out.println("САСИ БАЛЬШИЕ ПЕНИСЫ УБЛЮДАК");
                break;
            default:
                System.out.println("Неправельно введено название команды");
                System.out.println("Необходимо ввести только первое слово команды без аргументов!");
        }
    }

    /**
     * Метод осуществляет конец работы программы
     */
    public void exit() {
        System.exit(0);
    }

    /**
     * Метод, осуществляющий загрузку из файла.
     * @see QueueController
     * @return
     * Возвращает новый объект класса QueueController, который может реализовывать методы работы с коллекцией
     */
    public QueueController load() {
        return new QueueController(this.jsonParser);
    }

    /**
     * Метод, который выводит всю коллекцию на экран в формате сокращенного json
     * @param queueController
     * В качестве параметра на вход поступает объект класса queueController, который исполняет вывод коллекции на экран
     */
    public void show(QueueController queueController) {
        queueController.showQueue();
    }

    /**
     * Метод выводит общую информацию о коллекции
     * @param queueController
     * В качестве параметра на вход поступает объект класса queueController, который содержит в себе коллекцию
     */
    public void info(QueueController queueController) {
        System.out.println("Информация о коллекции: ");
        System.out.println("В коллекции содержаться объекты типа Person");
        if (queueController.getQueue().size() > 0) {
            System.out.println("В коллекции сейчас находится " + queueController.getQueue().size() + " элементов. Они приведены ниже");
            queueController.getQueue().forEach(person -> System.out.println("Name: " + person.getName() + ", Id:" + person.getId()));
            System.out.println("================================================");
        } else System.out.println("Коллекция пуста");

    }

    /**
     * Метод сохраняет все элементы коллекции в файл
     * @param queueController
     * В качестве параметра на вход поступает объект класса queueController, который содержит в себе колекцию
     *
     */
    public void save(QueueController queueController){
        if (jsonParser.isWritable()) {
            try {
                if (queueController.getQueue().size() > 0) {
                    Queue<Person> bufQueue = new PriorityQueue<>(queueController.getQueue());
                    jsonParser.resetSaveFile();
                    bufQueue.forEach(person -> {
                        try {
                            jsonParser.savePerson(person);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    });
                    System.out.println("Коллекция сохранена.");
                }
            } catch (NullPointerException e) {
                boolean isWorking = true;
                String question = terminal.readLine("Ваша коллекция пуста. Вы хотите удалить содержимое файла? Y/N");
                while (isWorking) {
                    if (question.equals("Y") || question.equals("y")) {
                        isWorking = false;
                        jsonParser.resetSaveFile();
                        System.out.println("Вы очистили содержимое файла");
                    } else if (question.equals("N") || question.equals("n")) {
                        isWorking = false;
                        System.out.println("Вы решили сохранить содержимое");
                    } else System.out.println("Неверная комманда. Введите Y или N");
                }
            }
        } else System.out.println("Недостаточно прав для изменения коллекции");


    }

    /**
     * Метод, реаллизующий вывод уникальных значений height
     * @param queueController
     * В качестве параметра на вход поступает объект класса queueController, который содержит в себе колекцию
     *
     */
    public void printUniqueHeight(QueueController queueController) {
        Queue<Person> buf = new PriorityQueue<>(queueController.getQueue());
        Person person;
        int kol = 1;
        boolean isUnique;
        float[] uniqueHeight = new float[buf.size()];
        if (queueController.getQueue().size() > 0) {
            System.out.println("Уникальные значения height: ");
            while ((person = buf.poll()) != null) {
                isUnique = true;
                for (int i = 0; i < kol; i++) {
                    if (uniqueHeight[i] == person.getHeight()) {
                        isUnique = false;
                        break;
                    }
                }
                if (isUnique) {
                    uniqueHeight[kol - 1] = person.getHeight();
                    kol++;
                    System.out.println(person.getHeight());
                }
            }
        } else System.out.println("Коллекция пуста");
    }

    /**
     *
     * Метод добавляет новый объект, если он меньше остальных объектов коллекции
     * @param queueController
     * В качестве параметра на вход поступает объект класса queueController, который содержит в себе колекции
     * @param arg1
     * В качестве второго параметра на вход идет аргумент строки, содержаший объект для сравнения
     * @throws SavePeopleException
     * Может возникнуть в случае ошибки добавления объекта в коллекцию
     * @throws NullPointerException
     * Может возникнуть в случае ошибки работы программы
     */
    public void addIfMin(QueueController queueController, String arg1) throws SavePeopleException, NullPointerException {
        if (queueController.getQueue().size() > 0) {
            Person p = commandTranslator.translateArg(arg1);
            Queue<Person> buf = new PriorityQueue<>(queueController.getQueue());
            Person min = buf.poll();
            for (Person person : buf) {
                assert min != null;
                if (person.compareTo(min) < 0) min = person;
            }
            assert min != null;
            if (p.compareTo(min) < 0) {
                queueController.getQueue().offer(p);
                System.out.println("Объект с именем " + p.getName() + " добавлен");
            } else System.out.println("Данный элемент не является минимальным");
        } else System.out.println("Коллекция пуста");

    }

    /**
     * Метод выводит все элементы коллекции, которые начнинаются с подстроки
     * @param queueController
     * В качестве параметра на вход поступает объект класса queueController, который содержит в себе колекцию
     * @param arg1
     * В качестве второго параметра на ввод поступает подстрока, по которой идет проверка
     */
    public void filterStartsWithName(QueueController queueController, String arg1) {
        Queue<Person> bufQueue = new PriorityQueue<>(queueController.getQueue());
        bufQueue.stream().filter(person -> person.getName().substring(0, arg1.length()).equals(arg1)).forEach(person ->
                System.out.println("Имя: " + person.getName() + ", id: " + person.getId()));
        if (bufQueue.stream().filter(person -> person.getName().substring(0, arg1.length()).equals(arg1)).count() <= 0) {
            System.out.println("В коллекции нету элементов, начинающихся на данную строку");
        }
    }

    /**
     * Метод подсчитывает элементы коллекции с нужным цветом волос
     * @param queueController
     * В качестве параметра на вход поступает объект класса queueController, который содержит в себе колекции
     * @param hairColor
     * Параметр хранит в себе искомый цвет волос
     */
    public void countByHairColor(QueueController queueController, HairColor hairColor) {
        System.out.println("В коллекции содержится " +
                queueController.getQueue().stream().filter(person -> person.getHairColor().equals(hairColor)).count() +
                " элементов с данным цветом волос");
    }

    /**
     * Метод удаляет объект коллекции с данным id
     * @param queueController
     * В качестве параметра на вход поступает объект класса queueController, который содержит в себе колекцию
     * @param id
     * Второй аргумент хранит в себе id нужного элемента
     */
    public void removeById(QueueController queueController, long id) {
        queueController.getQueue().removeIf(person -> person.getId() == id);
    }

    /**
     * Метод выводит последние 6 комманд
     * @param history
     * В качестве параметра на вход идет история введеных команд
     */
    public void history(History history) {
        history.printHistory();
    }

    /**
     * Метод обновляет элемент коллекции с нужным id
     * @param queueController
     * В качестве параметра на вход поступает объект класса queueController, который содержит в себе колекцию
     * @param id
     * Параметр id хранит в себе id нужного объекта
     * @param arg2
     * Параметр arg2 хранит в себе параметры, которые нужно изменить
     * @throws FindPersonException
     * @throws SavePeopleException
     */
    public void update(QueueController queueController, long id, String arg2) throws FindPersonException, SavePeopleException {
        if (queueController.getPersonById(id) != null) {
            Person bufPerson = queueController.getPersonById(id);
            removeById(queueController, id);
            if (queueController.getQueue().offer(commandTranslator.translateArg(arg2, bufPerson)))
                System.out.println("Элемент с id " + id + " был обновлен");
            else System.out.println("Ошибка");
        } else System.out.println("Введен неверный Id");
    }

    /**
     * Метод очищает коллекцию
     * @param queueController
     * В качестве параметра на вход поступает объект класса queueController, который содержит в себе колекцию
     */
    public void clear(QueueController queueController) {
        Person person;
        while ((person = queueController.getQueue().poll()) != null) {
            System.out.println("Был удален объект с id " + person.getId());
        }
    }

    /**
     * Метод добавляет элемент коллекции, собирая его из аргумента. Недостающие элементы генерируются случайным образом
     * @param queueController
     * В качестве параметра на вход поступает объект класса queueController, который содержит в себе колекцию
     * @param arg1
     * Параметр хранит в себе элементы, которые нужно добавить в объект. Остальные элементы генерируются автоматически
     * @throws SavePeopleException
     */
    public void add(QueueController queueController, String arg1) throws SavePeopleException {
        boolean somethingWrong = false;
        try {
            commandTranslator.translateArg(arg1);
        } catch (NullPointerException e) {
            somethingWrong = true;
        }
        if (!somethingWrong) {
            Person person = commandTranslator.translateArg(arg1);
            try {
                if (queueController.getQueue().offer(person)) System.out.println("Персонаж добавлен");
            } catch (NullPointerException e) {
                System.out.println("Ошибка");
            }
        } else
            System.out.println("Неверно введен второй аргумент комманды");
    }

    /**
     * Метод добавляет элемент коллекции, собирая его построчно.
     * @param queueController
     * В качестве параметра на вход поступает объект класса queueController, который содержит в себе колекцию
     * @throws SavePeopleException
     */
    public void add(QueueController queueController) {
        Person person = new Person();
        String buf;
        while (true) {
            buf = terminal.readLine("Введите имя");
            if (!buf.equals("")) {
                person.setName(buf);
                break;
            } else System.out.println("Имя введено неверно");
        }
        while (true) {
            try {
                buf = terminal.readLine("Введите координату X");
                if (!buf.equals("") && (Long.parseLong(buf) < Long.MAX_VALUE && Long.parseLong(buf) > Long.MIN_VALUE)) {
                    person.getCoordinates().setX(Long.parseLong(buf));
                    break;
                } else System.out.println("Координата X введена неверно");
            } catch (IllegalArgumentException e) {
                System.out.println("Координата X введена неверно");
            }
        }
        while (true) {
            try {
                buf = terminal.readLine("Введите координату Y");
                if (!buf.equals("") && (Float.parseFloat(buf) < Float.MAX_VALUE || Float.parseFloat(buf) > Float.MIN_VALUE)) {
                    person.getCoordinates().setY(Float.parseFloat(buf));
                    break;
                } else System.out.println("Координата Y введена неверно");
            } catch (IllegalArgumentException e) {
                System.out.println("Координата Y введена неверно");
            }
        }
        while (true) {
            try {
                buf = terminal.readLine("Введите рост");
                if (!buf.equals("") && (Float.parseFloat(buf) > 0 || Float.parseFloat(buf) < Float.MAX_VALUE)) {
                    person.setHeight(Float.parseFloat(buf));
                    break;
                } else System.out.println("Рост введен неверно");
            } catch (IllegalArgumentException e) {
                System.out.println("Рост введена неверно");
            }
        }
        while (true) {
            try {
                buf = terminal.readLine("Введите цвет глаз (Возможные цвета: RED, BLUE, YELLOW)");
                if (!buf.equals("")) {
                    person.setEyeColor(EyeColor.valueOf(buf.toUpperCase()));
                    break;
                } else System.out.println("Цвет глаз введен неверно");
            } catch (IllegalArgumentException e) {
                System.out.println("Цвет глаз введен неверно");
            }
        }
        while (true) {
            try {
                buf = terminal.readLine("Введите цвет волос (Возможные цвета: GREEN, BLACK, PINK, YELLOW, ORANGE, WHITE)");
                if (!buf.equals("")) {
                    person.setHairColor(HairColor.valueOf(buf.toUpperCase()));
                    break;
                } else System.out.println("Цвет волос введен неверно");
            } catch (IllegalArgumentException e) {
                System.out.println("Цвет волос введен неверно");
            }
        }
        while (true) {
            try {
                buf = terminal.readLine("Введите национальность (Возможные национальности: INDIA, VATICAN, NORTH_AMERICA, JAPAN)");
                if (!buf.equals("")) {
                    person.setNationality(Country.valueOf(buf.toUpperCase()));
                    break;
                } else System.out.println("Национальность введена неверно");
            } catch (IllegalArgumentException e) {
                System.out.println("Национальность введена неверно");
            }
        }
        while (true) {
            try {
                buf = terminal.readLine("Введите координату локации X");
                if (!buf.equals("") && (Integer.parseInt(buf) < Integer.MAX_VALUE || Integer.parseInt(buf) > Integer.MIN_VALUE)) {
                    person.getLocation().setX(Integer.parseInt(buf));
                    break;
                } else System.out.println("Координата X введена неверно");
            } catch (IllegalArgumentException e) {
                System.out.println("Координата X введена неверно");
            }
        }
        while (true) {
            try {
                buf = terminal.readLine("Введите координату локации Y");
                if (!buf.equals("") && (Float.parseFloat(buf) < Float.MAX_VALUE || Float.parseFloat(buf) > Float.MIN_VALUE)) {
                    person.getLocation().setY(Float.parseFloat(buf));
                    break;
                } else System.out.println("Координата Y введена неверно");
            } catch (IllegalArgumentException e) {
                System.out.println("Координата Y введена неверно");
            }
        }
        while (true) {
            try {
                buf = terminal.readLine("Введите координату локации Z");
                if (!buf.equals("") && (Float.parseFloat(buf) < Float.MAX_VALUE || Float.parseFloat(buf) > Float.MIN_VALUE)) {
                    person.getLocation().setY(Float.parseFloat(buf));
                    break;
                } else System.out.println("Координата Z введена неверно");
            } catch (IllegalArgumentException e) {
                System.out.println("Координата Z введена неверно");
            }
        }
        if (queueController.getQueue().offer(person)) System.out.println("Персонаж добавлен");
        else System.out.println("Произошла ошибка");

    }

    /**
     * Метод удаляет все элементы коллекции, меньше заданного
     * @param queueController
     * В качестве параметра на вход поступает объект класса queueController, который содержит в себе колекцию
     * @param arg1
     * Аргумент хранит в себе объект, с которым будут сравниваться другие элементы коллекции
     */
    public void remove_lower(QueueController queueController, String arg1) {
        try {
            Person p = commandTranslator.translateArg(arg1);
            queueController.getQueue().removeIf(person -> person.compareTo(p) < 0);
        } catch (SavePeopleException e) {
            e.printErr();
        }
    }

    /**
     * Метод исполняет скрипт из файла
     * @param queueController
     * В качестве параметра на вход поступает объект класса queueController, который содержит в себе колекцию
     * @param arg1
     * Аргумент хранит в себе путь до файла
     * @param history
     * Аргумент хранит в себе историю Комманд и скриптов
     * @param controller
     * Аргумент хранит в себе controller
     * @throws IOException
     */
    public void executeScript(QueueController queueController, String arg1, History history, Controller controller) throws IOException {
        File file = new File(arg1);
        if (file.canRead()) {
            Scanner sc = new Scanner(file);
            Command command;
            while (sc.hasNextLine()) {
                command = commandTranslator.translateCommand(sc.nextLine());
                switch (command.getCommand().toLowerCase()) {
                    case "help":
                        if (!command.getArg1().equals(""))
                            System.out.println("В данной команде нету второго аргумента");
                        else
                            controller.help();
                        break;
                    case "info":
                        if (!command.getArg1().equals(""))
                            System.out.println("В данной команде нету второго аргумента");
                        else
                            controller.info(queueController);
                        break;
                    case "remove_lower":
                        int size = queueController.getQueue().size();
                        controller.remove_lower(queueController, command.getArg1());
                        if (size == queueController.getQueue().size()) System.out.println("Коллекция не изменилась");
                        else
                            System.out.println("Было удалено " + (size - queueController.getQueue().size()) + " объектов");
                        break;
                    case "add_if_min":
                        try {
                            controller.addIfMin(queueController, command.getArg1());
                        } catch (SavePeopleException | NullPointerException e) {
                            System.out.println("Ошибка");
                            e.printStackTrace();
                        }
                        break;
                    case "history":
                        if (!command.getArg1().equals(""))
                            System.out.println("В данной команде нету второго аргумента");
                        else
                            controller.history(history);
                        break;
                    case "update":
                        try {
                            controller.update(queueController, Long.parseLong(command.getArg1()), command.getArg2());
                        } catch (SavePeopleException | FindPersonException e) {
                            e.printErr();
                        }
                        break;
                    case "execute_script":
                        if (history.addScriptPath(command.getArg1())) {
                            controller.executeScript(queueController, command.getArg1(), history, controller);
                        } else System.out.println("Данный скрипт приведет к бесконечному циклу");
                        break;
                    case "remove_by_id":
                        int queueSize = queueController.getQueue().size();
                        controller.removeById(queueController, Long.parseLong(command.getArg1()));
                        if (queueController.getQueue().size() == queueSize) System.out.println("Введен неверный Id");
                        else System.out.println("Удаление произошло успешно");
                        break;
                    case "clear":
                        if (!command.getArg1().equals(""))
                            System.out.println("В данной команде нету второго аргумента");
                        else
                            controller.clear(queueController);
                        break;
                    case "show":
                        if (!command.getArg1().equals(""))
                            System.out.println("В данной команде нету второго аргумента");
                        else
                            controller.show(queueController);
                        break;
                    case "save":
                        if (!command.getArg1().equals(""))
                            System.out.println("В данной команде нету второго аргумента");
                        else
                            controller.save(queueController);
                        break;
                    case "filter_starts_with_name":
                        controller.filterStartsWithName(queueController, command.getArg1());
                        break;
                    case "count_by_hair_color":
                        try {
                            controller.countByHairColor(queueController, HairColor.valueOf(command.getArg1().toUpperCase()));
                        } catch (IllegalArgumentException e) {
                            e.getMessage();
                            System.out.println("Такого цвета волос не существует");
                        }
                        break;
                    case "add":
                        if (!command.getArg1().equals("")) {
                            try {
                                controller.add(queueController, command.getArg1());
                            } catch (SavePeopleException e) {
                                e.printErr();
                            }
                        } else controller.add(queueController);
                        break;
                    case "exit":
                        if (!command.getArg1().equals(""))
                            System.out.println("В данной команде нету второго аргумента");
                        else
                            return;
                        break;
                    case "print_unique_height":
                        controller.printUniqueHeight(queueController);
                        break;
                    default:
                        System.out.println("Неверно введена команда");
                }
            }
        } else System.out.println("Недостаточно прав для исполнения файла");
        history.popScriptPath(arg1);

    }

    public void create_file(String filePath) {

    }
}



