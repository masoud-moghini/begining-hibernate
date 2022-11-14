package hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.SessionUtil;

import static org.testng.Assert.assertEquals;

public class ValidateSampleObject {
    public static SimpleObject validate(Long id,Long key,String value){
        SimpleObject so;
        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            so = session.get(SimpleObject.class,id);
            assertEquals(so.getKey(),key);
            assertEquals(so.getValue(),value);
            tx.commit();
        }
        return so;
    }
}
