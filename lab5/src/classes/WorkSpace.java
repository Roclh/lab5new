package classes;


import enums.HairColor;
import exceptionClasses.FindPersonException;
import exceptionClasses.SavePeopleException;
import interfaces.ICommandTranslator;
import wrappers.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


/* Основной цикл работы программы. Здесь определяется поведение программы в зависиомсти от введенных пользователем параметров
 *
 *
 *
 * */

public class WorkSpace {
    public WorkSpace() throws IOException {
        Controller controller = new Controller();
        try {
            ICommandTranslator commandTranslator = new CommandTranslator();
            History history = new History();
            QueueController queueController;
            Terminal terminal = new Terminal();
            Command command;
            boolean isWorking = true;
            queueController = controller.load();
            while (isWorking) { //Основной цикл работы программы
                command = commandTranslator.translateCommand(terminal.readLine("Введите команду"));
                history.add(command.getCommand());
                switch (command.getCommand().toLowerCase()) {
                    case "help":
                        if (!command.getArg2().equals(""))
                            System.out.println("В данной команде нету третьего аргмента");
                        else if (!command.getArg1().equals(""))
                            controller.help(command.getArg1());
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
                        if(history.addScriptPath(command.getArg1())) {
                            try {
                                controller.executeScript(queueController, command.getArg1(), history, controller);
                            }catch (FileNotFoundException e){
                                System.out.println("Такого скрипта не сущетвует");
                                history.popScriptPath(command.getArg1());
                            }
                        }
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
                            isWorking = controller.exit();
                        break;
                    case "print_unique_height":
                        controller.printUniqueHeight(queueController);
                        break;
                    default:
                        System.out.println("Неверно введена команда");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Не найден файл сохранения коллекции. Программа будет остановленна");
        }
    }

}
