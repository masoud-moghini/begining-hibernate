package hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.*;

import static org.testng.Assert.assertEquals;

public class PersonTest {

    SessionFactory factory;


    @BeforeClass
    public void setUp(){
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();

        factory = new MetadataSources(registry)
                .buildMetadata()
                .buildSessionFactory();
    }

    public Person savePerson(Session session,String name){
        Person p = new Person();
        p.setName("masoud");

        Transaction tx = session.beginTransaction();
            session.persist(p);
            tx.commit();
        return p;
    }


    @Test
    public void testSavingPerson(){

        try(Session session = factory.openSession()){
            Person p = savePerson(session,"test");
            assertEquals(p.getName(),"test");
        }
        return;
    }


}
