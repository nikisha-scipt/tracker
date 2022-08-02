package ru.job4j.tracker.jdbc;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
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
        Session session = sf.openSession();
        session.beginTransaction();
        Item res = session.get(Item.class, id);
        res.setName(item.getName());
        res.setDescription(item.getDescription());
        res.setCreated(item.getCreated());

        /* session.createQuery("update Item i set i.name = :fid, " +
                "i.description = :fdesciption, " +
                "i.created = :fcreated where i.id = :fid")
                        .setParameter("fid", item.getName())
                        .setParameter("fdesciption", item.getDescription())
                        .setParameter("fcreated", item.getCreated())
                        .executeUpdate(); */

        session.getTransaction().commit();
        return true;
    }

    @Override
    public boolean delete(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("delete from Item where id = :fid")
                .setParameter("fid", id)
                .executeUpdate();
        session.getTransaction().commit();
        return true;
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
