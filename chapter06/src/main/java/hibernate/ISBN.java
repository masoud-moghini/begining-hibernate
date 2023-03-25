package hibernate;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ISBN implements Serializable {
    @Column(name = "group_number")
    int group;
    int publisher;
    int title;
    int checkDigit;

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public int getPublisher() {
        return publisher;
    }

    public void setPublisher(int publisher) {
        this.publisher = publisher;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getCheckDigit() {
        return checkDigit;
    }

    public void setCheckDigit(int checkDigit) {
        this.checkDigit = checkDigit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ISBN isbn = (ISBN) o;
        return group == isbn.group && publisher == isbn.publisher && title == isbn.title && checkDigit == isbn.checkDigit;
    }

    @Override
    public String toString() {
        return "ISBN{" +
                "group=" + group +
                ", publisher=" + publisher +
                ", title=" + title +
                ", checkDigit=" + checkDigit +
                '}';
    }

    @Override
    public int hashCode() {
        int result = group;
        result = 31 * result + publisher;
        result = 31 * result + title;
        result = 31 * result + checkDigit;
        return result;
    }
}
