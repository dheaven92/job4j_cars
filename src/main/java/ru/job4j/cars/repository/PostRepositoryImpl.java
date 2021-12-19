package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import ru.job4j.cars.model.Post;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

public class PostRepositoryImpl implements PostRepository {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure()
            .build();

    private final SessionFactory sessionFactory = new MetadataSources(registry)
            .buildMetadata()
            .buildSessionFactory();

    private PostRepositoryImpl() {

    }

    private static final class PostRepositoryHolder {
        private final static PostRepository INSTANCE = new PostRepositoryImpl();
    }

    public static PostRepository instanceOf() {
        return PostRepositoryHolder.INSTANCE;
    }

    @Override
    public List<Post> findAllInLastDay() {
        return executeTransaction(session -> {
            Query<Post> query = session.createQuery(
                    "from Post post join fetch post.user where post.created > :yesterday"
            );
            query.setParameter("yesterday", new Date(System.currentTimeMillis() - 1000L * 60L * 60L * 24L));
            return query.list();
        });
    }

    @Override
    public List<Post> findAllWithPhoto() {
        return executeTransaction(session ->
                session.createQuery("from Post post where post.photo is not null").list());
    }

    @Override
    public List<Post> findAllByBrand(String brand) {
        return executeTransaction(session -> {
            Query<Post> query = session.createQuery("from Post post where post.brand = :brand");
            query.setParameter("brand", brand);
            return query.list();
        });
    }

    private <T> T executeTransaction(final Function<Session, T> command) {
        final Session session = sessionFactory.openSession();
        final Transaction transaction = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            transaction.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
