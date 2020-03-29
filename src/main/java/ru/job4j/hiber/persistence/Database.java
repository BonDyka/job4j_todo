package ru.job4j.hiber.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Database {
    private static final Logger LOG = LoggerFactory.getLogger(Database.class);

    public static final Database INSTANCE = new Database();

    private static final SessionFactory FACTORY;

    static {
        try {
            FACTORY = new Configuration().configure().buildSessionFactory();
        } catch (Exception e) {
            LOG.error("Initialization error", e);
            throw new InstantiationError("Initialization error");
        }
    }

    private Database() { }

    public Session openSession() {
        return FACTORY.openSession();
    }

    public void destroy() {
        FACTORY.close();
    }
}
