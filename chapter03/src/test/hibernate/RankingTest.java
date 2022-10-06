package hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.*;
public class RankingTest {

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

    public Ranking saveRank(Session session,
                         Integer rankingRate,
                         Person observer,
                         Person subject,
                         Skill skill
    ){
        Transaction tx = session.beginTransaction();
        Ranking ranking = new Ranking();
        ranking.setObserver(observer);
        ranking.setSubject(subject);
        ranking.setRanking(rankingRate);
        ranking.setSkill(skill);
        tx.commit();
        return ranking;

    }

    public Skill createSkill(Session session,String name){
        Transaction tx = session.beginTransaction();
        Skill skill = new Skill();
        skill.setName(name);
        tx.commit();
        return skill;
    }


    public Person savePerson(Session session,String name){
        Person p = new Person();
        p.setName(name);

        Transaction tx = session.beginTransaction();
        session.persist(p);
        tx.commit();
        return p;
    }
    @Test
    public void saveRankTest(){
        Ranking ranking = null;
        try(Session session = factory.openSession()){
            Transaction tx = session.beginTransaction();
            Person observer = savePerson(session,"masoud");
            Person subject = savePerson(session,"subject");
            Skill skill = createSkill(session,"JAVA");
            ranking = new Ranking(subject,observer,skill,2);
            tx.commit();
        }

        assertEquals(ranking.getRanking().intValue() ,2);
    }

}
