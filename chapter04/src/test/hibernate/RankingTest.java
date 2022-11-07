package hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import java.util.IntSummaryStatistics;
import java.util.stream.Collectors;

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

    private Ranking saveRank(Session session,
                         Ranking ranking
    ){

        session.persist(ranking);
        return ranking;

    }

    public Skill findSkill(Session session,String name){
        Query<Skill> query = session.createQuery("from Skill s where s.name= :name",Skill.class);
        query.setParameter("name",name);
        Skill skill = query.uniqueResult();
        return skill;
    }
    public Skill createSkill(Session session,String name){
        Skill skill = findSkill(session,name);
        if (skill==null){
            skill = new Skill();
            skill.setName(name);
            session.persist(skill);
        }
        return skill;
    }
    private Person savePerson(Session session,String name){
        Person p = new Person();
        p.setName(name);
        session.persist(p);
        return p;
    }
    private Ranking findRanking(Session session,String subject, String skill , String observer){
        Query<Ranking> query = session.createQuery("from Ranking r "+
                "where r.subject.name = :subject "+
                "and r.skill.name =: skill  " +
                "and r.observer.name =: observer",Ranking.class
        );
        query.setParameter("subject", subject);
        query.setParameter("skill", skill);
        query.setParameter("observer",observer);
        return query.uniqueResult();
    }
    private Ranking createData(Session session ,String observerName,String subjectName,String skillName,Integer rankingRate){
        Person observer = savePerson(session,observerName);
        Person subject = savePerson(session,subjectName);
        Skill skill = createSkill(session,skillName);
        Ranking ranking = new Ranking(subject,observer,skill,rankingRate);
        saveRank(session,ranking);
        return ranking;
    }
    private void populateRankingData(){
        try(Session session = factory.openSession()){
            Transaction tx = session.beginTransaction();
            createData(session,"Reza","Masoud","JAVA",3);
            createData(session,"Jamshid","Masoud","JAVA",5);
            createData(session,"Mahdi","Masoud","JAVA",7);
            tx.commit();
        }


    }

    public int getRankingAverage(){
        try(Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            Query<Ranking> query = session.createQuery("from Ranking r "+
                    "where r.subject.name = :subject "+
                    "and r.skill.name =: skill",Ranking.class
                    );
            query.setParameter("subject","Masoud");
            query.setParameter("skill","JAVA");
            IntSummaryStatistics stats = query.list().stream().collect(Collectors.summarizingInt(Ranking::getRanking));
            int average = (int)stats.getAverage();
            tx.commit();
            session.close();
            return average;
        }
    }

    @Test
    public void saveRankTest(){
        Ranking ranking = null;
        try(Session session = factory.openSession()){
            Transaction tx = session.beginTransaction();
            ranking = createData(session,"masoud","subject","JAVA",2);
            tx.commit();
        }

        assertEquals(ranking.getRanking().intValue() ,2);
    }

    @Test
    public void updateRanking(){
        populateRankingData();
        int average = getRankingAverage();
        assertEquals(average,5);
        try(Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            Ranking resultedRanking = findRanking(session,"Masoud","JAVA","Jamshid");
            resultedRanking.setRanking(11);
            tx.commit();
            average = getRankingAverage();
            assertEquals(average,7);
        }
    }

    @Test
    public void removeRanking(){
        populateRankingData();
        int average = getRankingAverage();
        assertEquals(average,5);
        try(Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            Ranking resultedRanking = findRanking(session,"Masoud","JAVA","Jamshid");
            assertNotNull(resultedRanking);
            session.delete(resultedRanking);
            tx.commit();
        }
        average = getRankingAverage();
        assertEquals(average,5);
    }
}
