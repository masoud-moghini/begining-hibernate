package hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.testng.annotations.Test;
import utils.SessionUtil;
import static hibernate.ValidateSampleObject.validate;
public class SimpleObjectTest {
    @Test
    public void testMerging(){
        SimpleObject obj;
        obj = new SimpleObject();
        obj.setKey(10L);
        obj.setValue("hello masoud");
        try(Session session = SessionUtil.getSession()){
            Transaction tx = session.beginTransaction();
            session.persist(obj);
            tx.commit();
        }
        var so = validate(obj.getId(), obj.getKey(), obj.getValue());
        obj.setKey(20L);

        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(obj);
            tx.commit();
        }
        validate(obj.getId(), obj.getKey(), obj.getValue());

    }

}
