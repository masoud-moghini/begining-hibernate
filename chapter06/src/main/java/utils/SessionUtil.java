package utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionUtil {
    private static final SessionUtil instance = new SessionUtil();
    private static final String CONFIG_NAME = "/configuration.properties";
    private SessionFactory factory;
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    private SessionUtil() {
        initialize();
    }

    public static Session getSession(){
        return getInstance().factory.openSession();
    }

    public static void forceReload(){
        getInstance().initialize();
    }

    private static SessionUtil getInstance() {
        return instance;
    }

    private void initialize() {
        logger.info("reloading factory");
        StandardServiceRegistry registry =
                new StandardServiceRegistryBuilder()
                        .configure()
                        .build();
        factory = new MetadataSources(registry)
                .buildMetadata()
                .buildSessionFactory();
    }
}
