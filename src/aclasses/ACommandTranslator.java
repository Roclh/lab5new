package aclasses;

/**
    @author Roclh

    @version 1.00

    Класс реализует перевод строк команд в нужный формат

    Метод translateCommand() превращает строку в отдельные команду и аргументы. Так как в программе может быть максимум
    3 аргумета, то строчка конвертируется в три отдельных слова и преобразуется в объект класса Command

    @see wrappers.Command



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

    /**
     * Переводчик команд. Подразумевает, что в программе не должно быть лишних пробелов, но на работу программы это не влияет
     * @param command
     * На вход попадает строка, в которой могут содержаться команда и 2 аргумента
     * @return
     * Возвращает объект класса Command
     * @see Command
     */
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

    /**
     * Метод translateArg() преобразует аргумент строки в формате json в объект класса Person
     *     Параметры, которых не было внутри аргумента, создаются автоматически случайным образом
     *     Также метод может реализовать копирование параметров переведенного аргумента в другой объект Person,
     *     если передать нужного Person в метод.
     * @param arg1
     * Аргумент командной строки, который мы преобразуем
     * @return
     * Возвращает объект класса Person, собранный из аргумента командной строки
     * @throws SavePeopleException
     * Может выбрасывать исключение в случае невозможности перевести параметр строки
     */
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
                person.getCoordinates().setX(Long.valueOf(buf.substring(buf.indexOf("\"X\":\"") + 5,
                        buf.indexOf("\";", buf.indexOf("\"X\":\"") + 5))));
                person.getCoordinates().setY(Float.parseFloat(buf.substring(buf.indexOf("\"Y\":\"") + 5,
                        buf.indexOf("\";", buf.indexOf("\"Y\":\"") + 5))));
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                throw SaveException;
            }
        } else {
            person.getCoordinates().setX((long) (Math.random() * 1000 - 500));
            person.getCoordinates().setY((float) Math.random() * 1000f - 500f);
        }
        if (buf.contains("\"height\":\"")) try {
            person.setHeight(Float.parseFloat(buf.substring(
                    buf.indexOf("\"height\":\"") + 10, buf.indexOf("\";",
                            buf.indexOf("\"height\":\"") + 10))));
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw SaveException;
        }
        else person.setHeight((float) Math.random() * 70f + 130f);
        if (buf.contains("\"eyeColor\":\"")) try {
            person.setEyeColor(EyeColor.valueOf(
                    buf.substring(buf.indexOf("\"eyeColor\":\"") + 12, buf.indexOf("\";",
                            buf.indexOf("\"eyeColor\":\"") + 12)).toUpperCase()));
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

    /**
     *  Такой-же метод, как и предыдущий, только запись идет в конкретного Person, который уже имеет какие-то значения
     *
     * @param person
     * Параметр, который содержит в себе объект типа Person, параметры которого нужно изменить
     */
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
