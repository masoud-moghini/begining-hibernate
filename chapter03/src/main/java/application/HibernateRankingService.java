package application;

import hibernate.Person;
import org.hibernate.Session;
import org.hibernate.query.Query;


public class HibernateRankingService {
    public Person findPerson(Session session, String name){
        Query<Person> query = session.createQuery("from Person p where p.name = :name" ,Person.class);
        query.setParameter("name",name);
        Person foundPerson = query.uniqueResult();
        return foundPerson;
    }
    private Person savePerson(Session session, String name) {
        Person person = findPerson(session, name);
        if (person == null) {
            person = new Person();
            person.setName(name);
            session.save(person);
        }
        return person;
    }
}
