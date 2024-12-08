package telran.citizens.dao;

import telran.citizens.model.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class CitizensImpl implements Citizens{
    private List<Person> idList;
    private List<Person> lastNameList;
    private List<Person> ageList;

    private  static Comparator<Person> lastNameComparator = (Person n1, Person n2) -> {
        int check = n1.getLastName().compareTo(n2.getLastName());
        return check != 0 ? check : Integer.compare(n1.getId(),n2.getId());
    };
    private  static Comparator<Person> ageComparator = (Person n1, Person n2) -> {
        int check = Integer.compare(n1.getAge(),n2.getAge());
        return check != 0 ? check : Integer.compare(n1.getId(),n2.getId());
    };



    public CitizensImpl() {
        idList = new ArrayList<>();
        lastNameList = new ArrayList<>();
        ageList = new ArrayList<>();

    }


    public CitizensImpl(List<Person> citizens) {
        this();
        if (citizens != null) {
            for (Person person : citizens) {
                add(person);
            }
        }
    }

    // O(n)
    @Override
    public boolean add(Person person) {
        if(person == null || find(person.getId()) != null){
            return false;
        }
        int index = -Collections.binarySearch(idList,person) - 1;
        idList.add(index, person);

        index = -Collections.binarySearch(lastNameList,person,lastNameComparator) - 1;
        lastNameList.add(index,person);

        index = -Collections.binarySearch(ageList,person,ageComparator) - 1;
        ageList.add(index,person);
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
        int index = Collections.binarySearch(idList,temp);
        return index >= 0 ? idList.get(index) : null;
    }

    // O(n)
    public Iterable<Person> find(int minAge, int maxAge) {
        return findByPredicate(person -> {
            int age = person.getAge();
            return age >= minAge && age <= maxAge;
        });
    }

    // O(n)
    @Override
    public Iterable<Person> find(String lastName) {
        return findByPredicate(e -> e.getLastName().equals(lastName));
    }


    // O(n)
    private Iterable<Person> findByPredicate(Predicate<Person> predicate){
        List<Person> res = new ArrayList<>();
        for (Person person : idList){
            if(predicate.test(person)){
                res.add(person);
            }
        }
        return res;
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
