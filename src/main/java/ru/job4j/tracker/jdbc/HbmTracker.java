package ru.job4j.tracker.jdbc;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import ru.job4j.tracker.model.Item;

import java.util.List;

public class HbmTracker implements Store, AutoCloseable {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();


    @Override
    public Item add(Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(item);
        session.getTransaction().commit();
        return item;
    }

    @Override
    public boolean replace(int id, Item item) {
        boolean rsl = true;
        Session session = sf.openSession();
        session.beginTransaction();
        /* Query query = session.createQuery("update Item i set i.name = :fid, "
                + "i.description = :fdesciption, "
                + "i.created = :fcreated where i.id = :fid");
        query.setParameter("fid", item.getName());
        query.setParameter("fdesciption", item.getDescription());
        query.setParameter("fcreated", item.getCreated());
        rsl = query.executeUpdate() > 0; */
        session.saveOrUpdate(item);
        session.getTransaction().commit();
        return rsl;
    }

    @Override
    public boolean delete(int id) {
        boolean rsl;
        Session session = sf.openSession();
        session.beginTransaction();
        Query query = session.createQuery("delete from Item where id = :fid");
        query.setParameter("fid", id);
        rsl = query.executeUpdate() > 0;
        session.getTransaction().commit();
        return rsl;
    }

    @Override
    public List findAll() {
        List result;
        Session session = sf.openSession();
        session.beginTransaction();
        result = session.createQuery("from Item").stream().toList();
        session.getTransaction().commit();
        return result;
    }

    @Override
    public List<Item> findByName(String key) {
        List result;
        Session session = sf.openSession();
        session.beginTransaction();
        result = session.createQuery("from Item c where c.name = :fname").setParameter("fname", key).stream().toList();
        session.getTransaction().commit();
        return result;
    }

    @Override
    public Item findById(int id) {
        Item res;
        Session session = sf.openSession();
        session.beginTransaction();
        res = (Item) session.createQuery("from Item c where c.id = :fid").setParameter("fid", id).uniqueResult();
        session.getTransaction().commit();
        return res;
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(registry);
        sf.close();
    }
}
