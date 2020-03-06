package aclasses;

/*
    Список комманд:
    help : вывести справку по доступным командам
    info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)
    show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении
    add {element} : добавить новый элемент в коллекцию
    update id {element} : обновить значение элемента коллекции, id которого равен заданному
    remove_by_id id : удалить элемент из коллекции по его id
    clear : очистить коллекцию
    save : сохранить коллекцию в файл
    execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.
    exit : завершить программу (без сохранения в файл)
    add_if_min {element} : добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции
    remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный
    history : вывести последние 6 команд (без их аргументов)
    count_by_hair_color hairColor : вывести количество элементов, значение поля hairColor которых равно заданному
    filter_starts_with_name name : вывести элементы, значение поля name которых начинается с заданной подстроки
    print_unique_height height : вывести уникальные значения поля height
*/

import enums.Country;
import enums.EyeColor;
import enums.HairColor;
import exceptionClasses.SavePeopleException;
import wrappers.Command;
import interfaces.ICommandTranslator;
import wrappers.Person;

public abstract class ACommandTranslator implements ICommandTranslator {
    private SavePeopleException SaveException;

    public ACommandTranslator() {
    }

    //Переводчик команд. Подразумевает, что в коммандах не будут находится лишние пробелы, но на работу программы это не влияет
    public Command translateCommand(String command) {
        Command translatedCommand;
        if (!command.contains(" ")) {
            translatedCommand = new Command(command, "", "");
        } else if (command.indexOf(" ") == command.lastIndexOf(" ")) {
            translatedCommand = new Command(command.substring(0, command.indexOf(" ")), command.substring(command.indexOf(" ") + 1));
        } else {
            translatedCommand = new Command(command.substring(0, command.indexOf(" ")),
                    command.substring(command.indexOf(" ") + 1, command.indexOf(" ", command.indexOf(" ") + 1)),
                    command.substring(command.indexOf(" ", command.indexOf(" ") + 1) + 1));
        }

        return translatedCommand;
    }

    //Переводчик аргумента, который возвращает новый объект класса Person
    public Person translateArg(String arg1) throws SavePeopleException {
        String buf = arg1;
        Person person = new Person();
        if (!buf.contains("\"name\":") || (buf.indexOf("\"name\":\"") + 9 == buf.indexOf("\";"))) throw SaveException;
        if (buf.contains("\"name\":\"")) try {
            person.setName(buf.substring(buf.indexOf("\"name\":\"") + 8, buf.indexOf("\";", buf.indexOf("\"name\":\"") + 8)));
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw SaveException;
        }
        if (buf.contains("\"coordinates\"{\"X\":")) {
            try {
                person.getCoordinates().setX(Long.valueOf(buf.substring(buf.indexOf("\"X\":\"") + 5, buf.indexOf("\";", buf.indexOf("\"X\":\"") + 5))));
                person.getCoordinates().setY(Float.parseFloat(buf.substring(buf.indexOf("\"Y\":\"") + 5, buf.indexOf("\";", buf.indexOf("\"Y\":\"") + 5))));
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                throw SaveException;
            }
        } else {
            person.getCoordinates().setX((long) (Math.random() * 100 - 50));
            person.getCoordinates().setY((float) Math.random() * 100f - 50f);
        }
        if (buf.contains("\"height\":\"")) try {
            person.setHeight(Float.parseFloat(buf.substring(
                    buf.indexOf("\"height\":\"") + 10, buf.indexOf("\";", buf.indexOf("\"height\":\"") + 10))));
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw SaveException;
        }
        else person.setHeight((float) Math.random() * 130f + 70f);
        if (buf.contains("\"eyeColor\":\"")) try {
            person.setEyeColor(EyeColor.valueOf(
                    buf.substring(buf.indexOf("\"eyeColor\":\"") + 12, buf.indexOf("\";", buf.indexOf("\"eyeColor\":\"") + 12)).toUpperCase()));
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw SaveException;
        }
        else {
            switch ((int) (Math.floor(Math.random() * 3))) {
                case 0:
                    person.setEyeColor(EyeColor.BLUE);
                    break;
                case 1:
                    person.setEyeColor(EyeColor.YELLOW);
                    break;
                default:
                    person.setEyeColor(EyeColor.RED);
            }
        }
        if (buf.contains("\"hairColor\":\"")) try {
            person.setHairColor(HairColor.valueOf(
                    buf.substring(buf.indexOf("\"hairColor\":\"") + 13, buf.indexOf("\";", buf.indexOf("\"hairColor\":\"") + 13)).toUpperCase()));
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw SaveException;
        }
        else {
            switch ((int) (Math.floor(Math.random() * 6))) {
                case 0:
                    person.setHairColor(HairColor.BLACK);
                    break;
                case 1:
                    person.setHairColor(HairColor.GREEN);
                    break;
                case 2:
                    person.setHairColor(HairColor.ORANGE);
                    break;
                case 3:
                    person.setHairColor(HairColor.WHITE);
                    break;
                case 4:
                    person.setHairColor(HairColor.PINK);
                    break;
                default:
                    person.setHairColor(HairColor.YELLOW);
            }
        }
        if (buf.contains("\"nationality\":\"")) try {
            person.setNationality(Country.valueOf(
                    buf.substring(buf.indexOf("\"nationality\":\"") + 15, buf.indexOf("\";", buf.indexOf("\"nationality\":\"") + 15)).toUpperCase()));
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw SaveException;
        }
        else {
            switch ((int) (Math.floor(Math.random() * 4))) {
                case 0:
                    person.setNationality(Country.INDIA);
                    break;
                case 1:
                    person.setNationality(Country.JAPAN);
                    break;
                case 2:
                    person.setNationality(Country.NORTH_AMERICA);
                    break;
                default:
                    person.setNationality(Country.VATICAN);
            }
        }
        if (buf.contains("\"location\"{\"X\":")) {
            try {
                person.getLocation().setX(Integer.valueOf(buf.substring(buf.lastIndexOf("{\"X\":\"") + 6, buf.indexOf("\";", buf.lastIndexOf("{\"X\":\"") + 6))));
                person.getLocation().setY(Float.valueOf(buf.substring(buf.lastIndexOf("\"Y\":\"") + 5, buf.indexOf("\";", buf.lastIndexOf("\"Y\":\"") + 5))));
                person.getLocation().setZ(Float.valueOf(buf.substring(buf.lastIndexOf("\"Z\":\"") + 5, buf.indexOf("\"}", buf.lastIndexOf("\"Z\":\"") + 5))));
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                throw SaveException;
            }
        } else {
            person.getLocation().setX((int) (Math.random() * 100 - 50));
            person.getLocation().setY((float) (Math.random() * 100 - 50));
            person.getLocation().setZ((float) (Math.random() * 100 - 50));
        }
        return person;
    }

    //Так-же переводчик аргумента, но он заносит все данные из аргумента в конкретный объект класса Person
    public Person translateArg(String arg1, Person person) throws SavePeopleException {
        String buf = arg1;
        if (buf.contains("\"name\":\"")) try {
            person.setName(buf.substring(buf.indexOf("\"name\":\"") + 8, buf.indexOf("\";", buf.indexOf("\"name\":\"") + 8)));
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw SaveException;
        }
        if (buf.contains("\"coordinates\"{\"X\":")) {
            try {
                person.getCoordinates().setX(Long.valueOf(buf.substring(buf.indexOf("\"X\":\"") + 5, buf.indexOf("\";", buf.indexOf("\"X\":\"") + 5))));
                person.getCoordinates().setY(Float.parseFloat(buf.substring(buf.indexOf("\"Y\":\"") + 5, buf.indexOf("\";", buf.indexOf("\"Y\":\"") + 5))));
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                throw SaveException;
            }
        }
        if (buf.contains("\"height\":\"")) try {
            person.setHeight(Float.parseFloat(buf.substring(
                    buf.indexOf("\"height\":\"") + 10, buf.indexOf("\";", buf.indexOf("\"height\":\"") + 10))));
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw SaveException;
        }
        if (buf.contains("\"eyeColor\":\"")) try {
            person.setEyeColor(EyeColor.valueOf(
                    buf.substring(buf.indexOf("\"eyeColor\":\"") + 12, buf.indexOf("\";", buf.indexOf("\"eyeColor\":\"") + 12)).toUpperCase()));
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw SaveException;
        }
        if (buf.contains("\"hairColor\":\"")) try {
            person.setHairColor(HairColor.valueOf(
                    buf.substring(buf.indexOf("\"hairColor\":\"") + 13, buf.indexOf("\";", buf.indexOf("\"hairColor\":\"") + 13)).toUpperCase()));
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw SaveException;
        }
        if (buf.contains("\"nationality\":\"")) try {
            person.setNationality(Country.valueOf(
                    buf.substring(buf.indexOf("\"nationality\":\"") + 15, buf.indexOf("\";", buf.indexOf("\"nationality\":\"") + 15)).toUpperCase()));
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw SaveException;
        }
        if (buf.contains("\"location\"{\"X\":")) {
            try {
                person.getLocation().setX(Integer.valueOf(buf.substring(buf.lastIndexOf("{\"X\":\"") + 6, buf.indexOf("\";", buf.lastIndexOf("{\"X\":\"") + 6))));
                person.getLocation().setY(Float.valueOf(buf.substring(buf.lastIndexOf("\"Y\":\"") + 5, buf.indexOf("\";", buf.lastIndexOf("\"Y\":\"") + 5))));
                person.getLocation().setZ(Float.valueOf(buf.substring(buf.lastIndexOf("\"Z\":\"") + 5, buf.indexOf("\"}", buf.lastIndexOf("\"Z\":\"") + 5))));
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                throw SaveException;
            }
        }
        return person;
    }


}
