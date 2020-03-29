package ru.job4j.hiber.persistence;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.hiber.models.Item;

import java.util.List;
import java.util.function.Function;

public class ItemDao implements GenericDao<Item> {

    private static final Logger LOG = LoggerFactory.getLogger(ItemDao.class);

    @Override
    public void saveOrUpdate(Item entity) {
        Transaction tx = null;
        try (Session session = Database.INSTANCE.openSession()) {
            tx = session.beginTransaction();
            session.saveOrUpdate(entity);
            tx.commit();
        } catch (Exception e) {
            LOG.error("Transaction fail!", e);
            if (tx != null) {
                try {
                    tx.rollback();
                } catch (Exception e1) {
                    LOG.error("Rollback failed!", e1);
                }
            }
        }
    }

    @Override
    public void delete(Item entity) {
        Transaction tx = null;
        try (Session session = Database.INSTANCE.openSession()) {
            tx = session.beginTransaction();
            session.delete(entity);
            tx.commit();
        } catch (Exception e) {
            LOG.error("Transaction fail!", e);
            if (tx != null) {
                try {
                    tx.rollback();
                } catch (Exception e1) {
                    LOG.error("Rollback failed!", e1);
                }
            }
        }
    }

    @Override
    public Item read(long id) throws PersistException {
        Item item = this.tx(session ->  session.get(Item.class, id));
        if (item == null) {
            throw new PersistException("User not found!");
        }
        return item;
    }

    @Override
    public List<Item> readUndone() throws PersistException {
        return this.tx(
                session -> {
                    Query<Item> query = session.createQuery("from Item where done=:done", Item.class);
                    query.setParameter("done", false);
                    return query.list();
                }
        );
    }

    @Override
    public List<Item> readAll() throws PersistException {
        return this.tx(session -> session.createQuery("from Item", Item.class).list());
    }

    private <T> T tx(Function<Session, T> command) throws PersistException {
        Session session = Database.INSTANCE.openSession();
        Transaction tx = session.beginTransaction();
        try {
            return command.apply(session);
        } catch (Exception e) {
            LOG.error("Not complete operation", e);
            tx.rollback();
            throw new PersistException("Not complete operation", e);
        } finally {
            tx.commit();
            session.close();
        }
    }
}
