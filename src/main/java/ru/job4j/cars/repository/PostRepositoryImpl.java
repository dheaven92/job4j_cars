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
    public List<Post> findAllWithCarAndUserAndOrderByCreated() {
        return executeTransaction(session ->
                session.createQuery(
                        "from Post post join fetch post.car join fetch post.user order by post.created desc"
                ).list());
    }

    @Override
    public List<Post> findAllCreateInLastDay() {
        return executeTransaction(session -> {
            Query<Post> query = session.createQuery(
                    "from Post post join fetch post.car where post.created > :yesterday"
            );
            query.setParameter("yesterday", new Date(System.currentTimeMillis() - 1000L * 60L * 60L * 24L));
            return query.list();
        });
    }

    @Override
    public List<Post> findAllByCarBrandId(int brandId) {
        return executeTransaction(session -> {
            Query<Post> query = session.createQuery(
                    "from Post post join fetch post.car where post.car.brand.id = :brandId"
            );
            query.setParameter("brandId", brandId);
            return query.list();
        });
    }

    @Override
    public List<Post> findAllByCarBodyTypeId(int bodyTypeId) {
        return executeTransaction(session -> {
            Query<Post> query = session.createQuery(
                    "from Post post join fetch post.car where post.car.bodyType.id = :bodyTypeId"
            );
            query.setParameter("bodyTypeId", bodyTypeId);
            return query.list();
        });
    }

    @Override
    public Post savePost(Post post) {
        return executeTransaction(session -> {
            session.saveOrUpdate(post);
            return post;
        });
    }

    @Override
    public Post findById(int id) {
        return executeTransaction(session -> {
            Query<Post> query = session.createQuery("from Post post join fetch post.car where post.id = :id");
            query.setParameter("id", id);
            return query.uniqueResult();
        });
    }

    @Override
    public void delete(Post post) {
        executeTransaction(session -> {
            session.delete(post);
            return true;
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
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
