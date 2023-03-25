package hibernate.orphan;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Library {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    String name;


    //IF MY mappedBy ENTITY WAS REMOVED , i WILL BE ORPHAN ,OS KILL ME
    //@OneToMany(orphanRemoval = true,mappedBy = "library")
    //List<BookEmbeddable> books = new ArrayList<BookEmbeddable>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*
    public List<BookEmbeddable> getBooks() {
        return books;
    }
    */
    /*
    public void setBooks(List<BookEmbeddable> books) {
        this.books = books;
    }

    */
}
