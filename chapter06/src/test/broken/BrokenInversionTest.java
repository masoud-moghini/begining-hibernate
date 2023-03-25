package broken;

import hibernate.Email;
import hibernate.Message;
import hibernate.Person;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.testng.annotations.Test;
import utils.SessionUtil;

import static org.testng.Assert.*;

public class BrokenInversionTest {


    @Test
    public void testBrokenInversionCode(){
        Email email ;
        Long emailId ;

        Message message;
        Long messageId ;

        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            email = new Email("hello masoud");
            message= new Message("how are you");
            session.persist(email);
            session.persist(message);

            System.out.println(email.getSubject());
            message.setEmail(email);

            tx.commit();
            emailId = email.getId();
            messageId = message.getId();


        }
        assertNull(email.getMessage());
        assertNotNull(message.getEmail());

        try(Session session = SessionUtil.getSession()) {
            email = session.get(Email.class,emailId);
            System.out.println(email);
            message = session.get(Message.class,messageId);
            System.out.println(message);
        }

        assertNotNull(email.getMessage());
        assertNotNull(message.getEmail());
        assertEquals(email.getSubject(),"hello masoud");
        try(Session session = SessionUtil.getSession()) {
            email = session.get(Email.class,emailId);
            email.setSubject("hello from modified");
            session.saveOrUpdate(email);
        }

        assertEquals(email.getSubject(),"hello from modified");
    }
}
