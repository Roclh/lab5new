package interfaces;

import exceptionClasses.SavePeopleException;
import wrappers.Command;
import wrappers.Person;

public interface ICommandTranslator {
    Command translateCommand(String command);

    Person translateArg(String arg1) throws SavePeopleException;

    Person translateArg(String arg1, Person person) throws SavePeopleException;
}
