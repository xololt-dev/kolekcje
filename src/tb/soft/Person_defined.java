package tb.soft;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.AbstractCollection;
import java.util.Comparator;
import java.util.Objects;
import java.util.Iterator;

public class Person_defined extends Person implements Comparable<Person_defined>{
    public Person_defined(String first_name, String last_name) throws PersonException {
        super(first_name, last_name);
    }

    public Person_defined(Person person) throws PersonException {
        super(person.firstName, person.lastName);
        this.birthYear = person.birthYear;
        this.job = person.job;
    }

    @Override
    public int compareTo(Person_defined o) {
        return this.birthYear - o.birthYear;
    }

    @Override
    public int getBirthYear()
    {
        return this.birthYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person_defined person = (Person_defined) o;

        if (birthYear != person.birthYear) return false;
        if (!Objects.equals(firstName, person.firstName)) return false;
        //return lastName.equals(person.lastName);
        return true;
    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + lastName.hashCode();
        result = 31 * result + birthYear;
        return result;
    }

    public static void printTableToFileDefined(String file_name, AbstractCollection<Person_defined> person_defined) throws PersonException {
        try (PrintWriter writer = new PrintWriter(file_name)) {
            printTableToFileDefined(writer, person_defined);
        } catch (FileNotFoundException e){
            throw new PersonException("Nie odnaleziono pliku " + file_name);
        }
    }

    public static void printTableToFileDefined(PrintWriter writer, AbstractCollection<Person_defined> person_defined){
        Iterator<Person_defined> personIterator = person_defined.iterator();
        while(personIterator.hasNext())
        {
            Person_defined temporaryPerson = personIterator.next();
            writer.println(temporaryPerson.firstName + "#" + temporaryPerson.lastName +
                    "#" + temporaryPerson.birthYear + "#" + temporaryPerson.job);
        }
    }
}

class Person_definedYearCompare implements Comparator<Person_defined> {
    public int compare(Person_defined object1, Person_defined object2) {
        if (object1.getBirthYear() < object2.getBirthYear()) return -1;
        if (object1.getBirthYear() > object2.getBirthYear()) return 1;
        else return 0;
    }
}

class Person_definedLastNameCompare implements Comparator<Person_defined>{
    public int compare(Person_defined object1, Person_defined object2)
    {
        return object1.getLastName().compareTo(object2.getLastName());
    }
}