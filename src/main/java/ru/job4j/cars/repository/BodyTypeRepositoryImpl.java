package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.cars.model.BodyType;

import java.util.List;

public class BodyTypeRepositoryImpl implements BodyTypeRepository {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure()
            .build();

    private final SessionFactory sessionFactory = new MetadataSources(registry)
            .buildMetadata()
            .buildSessionFactory();

    private BodyTypeRepositoryImpl() {

    }

    private static final class BodyTypeRepositoryImplHolder {
        private static final BodyTypeRepository INSTANCE = new BodyTypeRepositoryImpl();
    }

    public static BodyTypeRepository instanceOf() {
        return BodyTypeRepositoryImplHolder.INSTANCE;
    }

    @Override
    public List<BodyType> findAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        try {
            List<BodyType> bodyTypes = session.createQuery("from BodyType").list();
            transaction.commit();
            return bodyTypes;
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
