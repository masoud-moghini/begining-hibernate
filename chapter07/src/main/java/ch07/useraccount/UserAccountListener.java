package ch07.useraccount;

import jakarta.persistence.PrePersist;

public class UserAccountListener {
    @PrePersist
    void setPasswordHash(Object o){
        UserAccount ua = (UserAccount) o;
        if (ua.getSalt() == null || ua.getSalt()==0){
            ua.setSalt((int) (Math.random()*65535));
        }
        ua.setPasswordHash(ua.getSalt()*ua.getPassword().hashCode());
    }
}
