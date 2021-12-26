package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import ru.job4j.cars.model.User;

public class UserRepositoryImpl implements UserRepository {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure()
            .build();

    private final SessionFactory sessionFactory = new MetadataSources(registry)
            .buildMetadata()
            .buildSessionFactory();

    private UserRepositoryImpl() {

    }

    private final static class UserRepositoryImplHolder {
        private final static UserRepository INSTANCE = new UserRepositoryImpl();
    }

    public static UserRepository instanceOf() {
        return UserRepositoryImplHolder.INSTANCE;
    }

    @Override
    public User createUser(User user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        try {
            session.save(user);
            transaction.commit();
            return user;
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public User findUserByEmail(String email) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        try {
            Query<User> query = session.createQuery("from User where email = :email");
            query.setParameter("email", email);
            User user = query.uniqueResult();
            transaction.commit();
            return user;
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
