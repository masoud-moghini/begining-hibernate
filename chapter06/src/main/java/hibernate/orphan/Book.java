package hibernate.orphan;


import hibernate.ISBN;
import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.TABLE;

@Entity
public class Book {
    @Id
    private ISBN id;

    @ManyToOne
    Library library;

    @Column
    private String title1;

    public ISBN getId() {
        return id;
    }

    public void setId(ISBN id) {
        this.id = id;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    public String getTitle() {
        return title1;
    }

    public void setTitle(String title) {
        this.title1 = title;
    }
}
