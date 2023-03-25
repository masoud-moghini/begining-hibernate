package hibernate.orphan;


import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.TABLE;

@Entity
public class Book {
    @Id
    @TableGenerator(name="tablegen",
            allocationSize = 1000,
            table="ID_TABLE",
            pkColumnName="ID_WHY",
            valueColumnName="NEXT_ID")
    @GeneratedValue(strategy=TABLE,generator="tablegen")
    private Long id;

    @ManyToOne
    Library library;

    @Column
    private String title;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
