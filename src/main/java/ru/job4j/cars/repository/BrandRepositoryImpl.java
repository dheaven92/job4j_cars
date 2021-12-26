package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.cars.model.Brand;

import java.util.List;

public class BrandRepositoryImpl implements BrandRepository {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure()
            .build();

    private final SessionFactory sessionFactory = new MetadataSources(registry)
            .buildMetadata()
            .buildSessionFactory();
    
    private BrandRepositoryImpl() {
        
    }
    
    private static final class BrandRepositoryImplHolder {
        private final static BrandRepository INSTANCE = new BrandRepositoryImpl();
    }

    public static BrandRepository instanceOf() {
        return BrandRepositoryImplHolder.INSTANCE;
    }
    
    @Override
    public List<Brand> findAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        try {
            List<Brand> brands = session.createQuery("from Brand").list();
            transaction.commit();
            return brands;
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
