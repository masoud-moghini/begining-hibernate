package hibernate;


import jakarta.persistence.*;

@Entity
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column
    String subject;


    public String getSubject() {
        return subject;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @OneToOne(mappedBy = "email")
    Message message;

    public Email(String subject) {
        this.subject = subject;
    }

    public Email() {
    }


    @Override
    public String toString() {
        return "Email{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                '}';
    }

}
