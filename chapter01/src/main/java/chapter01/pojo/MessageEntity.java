package chapter01.pojo;

import jakarta.persistence.*;

import java.util.Objects;


@Entity
public class MessageEntity {
    String text;
    Long id;

    public MessageEntity() {
    }

    public MessageEntity(String text, Long id) {
        this.text = text;
        this.id = id;
    }

    @Column(nullable = false)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageEntity that = (MessageEntity) o;
        return this.id.equals(that.id) && this.text.equals(that.text);
    }

    @Override
    public String toString() {
        return "MessageEntity{" +
                "text='" + text + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, id);
    }
}
