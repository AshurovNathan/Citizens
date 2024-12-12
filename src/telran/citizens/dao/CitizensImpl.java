package telran.citizens.dao;

import telran.citizens.model.Person;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;

public class CitizensImpl implements Citizens{
    private TreeSet<Person> idList;
    private TreeSet<Person> lastNameList;
    private TreeSet<Person> ageList;

    private  static Comparator<Person> lastNameComparator = (Person n1, Person n2) -> {
        int check = n1.getLastName().compareTo(n2.getLastName());
        return check != 0 ? check : Integer.compare(n1.getId(),n2.getId());
    };
    private  static Comparator<Person> ageComparator = (Person n1, Person n2) -> {
        int check = Integer.compare(n1.getAge(),n2.getAge());
        return check != 0 ? check : Integer.compare(n1.getId(),n2.getId());
    };

    public CitizensImpl() {
        idList = new TreeSet<>();
        lastNameList = new TreeSet<>(lastNameComparator);
        ageList = new TreeSet<>(ageComparator);

    }


    public CitizensImpl(List<Person> citizens) {
        this();
        citizens.forEach(p -> add(p));
    }

    // O(n)
    @Override
    public boolean add(Person person) {
        if(person == null || find(person.getId()) != null){
            return false;
        }
        idList.add(person);

        lastNameList.add(person);

        ageList.add(person);

        return true;
    }


    // O(n)
    @Override
    public boolean remove(int id) {
        Person victim = find(id);
        if(victim == null){
            return false;
        }

        idList.remove(victim);
        lastNameList.remove(victim);
        ageList.remove(victim);

        return true;
    }

    // O(log n)
    @Override
    public Person find(int id) {
        Person temp = new Person(id,"","",null);
        return idList.ceiling(temp);
    }

    // O(log n)
    public Iterable<Person> find(int minAge, int maxAge) {
        LocalDate now = LocalDate.now();
        Person from = new Person(Integer.MIN_VALUE,"","",now.minusYears(minAge));
        Person to = new Person(Integer.MAX_VALUE,"","", now.minusYears(maxAge));

        return ageList.subSet(from,true,to,true);
    }

    // O(log n)
    @Override
    public Iterable<Person> find(String lastName) {
        Person from = new Person(Integer.MIN_VALUE,"",lastName,null);
        Person to = new Person(Integer.MAX_VALUE,"",lastName, null);
        return lastNameList.subSet(from,true,to,true);
    }



    // O(1)
    @Override
    public Iterable<Person> getAllPersonsSortedById() {
        return idList;
    }

    // O(1)
    @Override
    public Iterable<Person> getAllPersonsSortedByAge() {
        return ageList;
    }

    // O(1)
    @Override
    public Iterable<Person> getAllPersonsSortedByLastName() {
        return lastNameList;
    }

    // O(1)
    @Override
    public int size() {
        return idList.size();
    }
}
