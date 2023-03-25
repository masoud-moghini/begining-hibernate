package hibernate;


import jakarta.persistence.*;

@Entity
public class Ranking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    private Person subject;

    @ManyToOne
    private Person observer;

    @ManyToOne
    private Skill skill;


    @Column
    private Integer ranking;

    public Ranking(){}

    public Ranking(Person subject, Person observer, Skill skill, Integer ranking) {
        this.subject = subject;
        this.observer = observer;
        this.skill = skill;
        this.ranking = ranking;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Person getSubject() {
        return subject;
    }

    public void setSubject(Person subject) {
        this.subject = subject;
    }

    public Person getObserver() {
        return observer;
    }

    public void setObserver(Person observer) {
        this.observer = observer;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }
}
