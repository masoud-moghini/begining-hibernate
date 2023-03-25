import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import model.Thing;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.testng.annotations.Test;
import utils.util.JPASessionUtil;

import static org.testng.Assert.*;

public class JPASessionUtilTest {
    @Test
    public void getEntityManager(){
        EntityManager em = JPASessionUtil.getEntityManager("jpautils");
        em.close();
    }
    @Test(expectedExceptions = {PersistenceException.class})
    public void nonExistedEntityManagerException(){
        JPASessionUtil.getEntityManager("nonexistent");
        fail("we shouldn't be able to get exception here");
    }
    @Test
    public void testEntityManager(){
        EntityManager em = JPASessionUtil.getEntityManager("jpautils");
        em.getTransaction().begin();
        Thing a = new Thing();
        a.setName("James");
        em.persist(a);
        em.getTransaction().commit();

        em.getTransaction().begin();
        TypedQuery<Thing> query = em.createQuery("from Thing a where a.name=:name ", Thing.class);
        query.setParameter("name","James");
        Thing fetchedQuery = query.getSingleResult();
        assertNotNull(fetchedQuery);
        assertEquals(fetchedQuery,a);
        em.getTransaction().commit();

        em.getTransaction().begin();
        em.remove(a);
        em.getTransaction().commit();
        em.close();

    }

    @Test
    public void testSession(){
        Thing t = null;
        try(Session session = JPASessionUtil.getSession("jpautils")) {
            Transaction tx = session.beginTransaction();
            t = new Thing();
            t.setName("James 2");
            session.persist(t);
            tx.commit();
        }
        try(Session session = JPASessionUtil.getSession("jpautils")) {
            Transaction tx = session.beginTransaction();
            Query<Thing> q = session.createQuery("from Thing t where t.name= :name",Thing.class);
            q.setParameter("name","James 2");
            Thing result = q.uniqueResult();
            assertNotNull(result);
            assertEquals(result,t);
            session.delete(result);
            tx.commit();
        }
    }
}
