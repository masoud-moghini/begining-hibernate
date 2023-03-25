package utils.util;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.hibernate.Session;

import java.util.HashMap;
import java.util.Map;

public class JPASessionUtil {
    public static Map<String, EntityManagerFactory>
            persistenceUnits= new HashMap<>();

    public static synchronized EntityManager
        getEntityManager(String persistentUnitName){
        persistenceUnits.putIfAbsent(persistentUnitName,
                Persistence.createEntityManagerFactory(persistentUnitName)
        );
        return persistenceUnits.get(persistentUnitName).createEntityManager();
    }

    public static Session getSession(String persistentUnitName){
        return getEntityManager(persistentUnitName)
                .unwrap(Session.class);
    }
}
