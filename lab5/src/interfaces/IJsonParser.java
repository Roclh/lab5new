package interfaces;

import wrappers.Person;
import exceptionClasses.SavePeopleException;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface IJsonParser {
    void savePerson(Person person) throws IOException;

    void setFile(String path);

    Person getSavedPerson(long id) throws IOException, SavePeopleException;

    void resetSaveFile() throws IOException;

    int getKolEl() throws FileNotFoundException;

    long[] getId() throws FileNotFoundException;
}
