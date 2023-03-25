package ch07.lifecyclething;

import jakarta.persistence.*;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.BitSet;

@Entity
@Data
public class LifeCycleThing {
    public static Logger logger = LoggerFactory.getLogger(LifeCycleThing.class);
    public static BitSet lifecycleCalls = new BitSet();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @PostLoad
    public void postLoad() {
        log("postLoad", 0);
    }
    @PrePersist
    public void prePersist() {
        log("prePersist", 1);
    }
    @PostPersist
    public void postPersist() {
        log("postPersist", 2);
    }
    @PreUpdate
    public void preUpdate() {
        log("preUpdate", 3);
    }
    @PostUpdate
    public void postUpdate() {
        log("postUpdate", 4);
    }
    @PreRemove
    public void preRemove() {
        log("preRemove", 5);
    }
    @PostRemove
    public void postRemove() {
        log("postRemove", 6);
    }
    private void log(String method, int index) {
        lifecycleCalls.set(index, true);
        logger.info("{}: {} {}", method,
                this.getClass().getSimpleName(), this);
    }
}
