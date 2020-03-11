package exceptionClasses;

import interfaces.SLPeopleExceptions;

public class SavePeopleException extends Exception implements SLPeopleExceptions {
    private String mes = "В файле сохранения нету такого id";

    public void printErr() {
        System.out.println(mes);
    }
}
