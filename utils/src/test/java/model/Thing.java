package model;

import jakarta.persistence.*;
import lombok.Data;


@Entity(name = "Thing")
@Data
public class Thing {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @Column
    String name;
}
