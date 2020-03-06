package exceptionClasses;

import interfaces.SLPeopleExceptions;

public class FindPersonException extends Exception implements SLPeopleExceptions {
    private String mes = "В коллекции нету человека с таким id";

    public void printErr() {
        System.out.println(mes);
    }

}
