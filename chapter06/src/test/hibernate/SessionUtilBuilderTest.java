package hibernate;


import org.hibernate.Session;
import org.testng.annotations.Test;
import utils.SessionUtil;
import static org.testng.Assert.assertNotNull;
public class SessionUtilBuilderTest {
    @Test
    public void testSessionFactory(){
        try(Session session = SessionUtil.getSession()){
            assertNotNull(session);

        }
    }
}
