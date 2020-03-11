package aclasses;

import interfaces.ITerminal;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;

/**
 * Класс, который реализует считывание строки в терминале
 */

public abstract class ATerminal implements ITerminal {
    static Scanner sc = new Scanner(System.in);

    /**
     * Метод, который выводит в терминал вопрос, после чего ждет строку на ввод
     * @param question
     * Вопрос, который метод выведет перед вводом
     * @return
     * Возвращает строку, которую ввел пользователь
     */
    public String readLine(String question) {
        System.out.println(question);
        while (true)
            try {
                return sc.nextLine();
            } catch (java.util.InputMismatchException e) {
                e.getMessage();
            }
    }

}
