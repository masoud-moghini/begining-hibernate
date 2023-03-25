package ch07.lifecyclething;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.testng.Reporter;
import org.testng.annotations.Test;
import utils.util.JPASessionUtil;

import static org.testng.Assert.*;

public class FirstLifecycleTest {
    @Test
    public void testLifecycle() {

        Long id;
        LifeCycleThing thing1, thing2, thing3;
        try (Session session = JPASessionUtil.getSession("chapter07")) {
            Transaction tx = session.beginTransaction();
            thing1 = new LifeCycleThing();
            thing1.setName("Thing 1");
            session.save(thing1);
            id = thing1.getId();
            System.out.println(thing1);
            tx.commit();
        }
        try (Session session = JPASessionUtil.getSession("chapter07")) {
            Transaction tx = session.beginTransaction();
            thing2 = session
                    .byId(LifeCycleThing.class)
                    .load(-1);
            assertNull(thing2);
            Reporter.log("attempted to load nonexistent reference");
            thing2 = session.byId(LifeCycleThing.class)
                    .getReference(id);
            assertNotNull(thing2);
            assertEquals(thing1, thing2);
            thing2.setName("Thing 2");
            tx.commit();
        }
        try (Session session = JPASessionUtil.getSession("chapter07")) {
            Transaction tx = session.beginTransaction();
            thing3 = session
                    .byId(LifeCycleThing.class)
                    .getReference(id);
            assertNotNull(thing3);
            assertEquals(thing2, thing3);
            session.delete(thing3);
            tx.commit();
        }
        assertEquals(LifeCycleThing.lifecycleCalls.nextClearBit(0), 7);


    }
}
