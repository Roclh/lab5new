package aclasses;

import interfaces.ITerminal;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;
//Класс считыванния данных со строки в терминале,

public abstract class ATerminal implements ITerminal {
    static Scanner sc = new Scanner(System.in);

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
