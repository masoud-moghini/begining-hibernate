package hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.mapping.MetadataSource;
import org.hibernate.query.Query;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import static org.testng.Assert.*;
public class SkillTest {

    private SessionFactory factory;
    @BeforeClass
    public void setUp(){
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();


        factory = new MetadataSources(registry)
                .buildMetadata()
                .buildSessionFactory();
    }

    public Skill findSkill(Session session,String name){
        Transaction tx = session.beginTransaction();
        Query<Skill> query = session.createQuery("from Skill s where s.name= :name",Skill.class);
        query.setParameter("name",name);
        Skill skill = query.uniqueResult();
        tx.commit();
        return skill;
    }
    public Skill createSkill(Session session,String name){
        Transaction tx = session.beginTransaction();
        Skill skill = findSkill(session,name);
        if (skill==null){
            skill.setName(name);
            session.persist(skill);
        }
        tx.commit();
        return skill;
    }


    @Test
    public void createSkillTest(){
        Skill skill;
        try(Session session= factory.openSession()){
            skill = createSkill(session,"JAVA");
        }
        assertEquals(skill.getName(),"JAVA");
    }
}
