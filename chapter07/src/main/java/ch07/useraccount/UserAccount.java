package ch07.useraccount;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@EntityListeners({UserAccountListener.class})
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    String name;

    @Transient
    String password;

    Integer salt;
    Integer passwordHash;

    public boolean validPassword(String newPass){
        return newPass.hashCode()*salt == getPasswordHash();
    }
}
