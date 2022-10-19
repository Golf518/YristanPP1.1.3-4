package jm.task.core.jdbc.dao;

import jakarta.persistence.TypedQuery;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private Session session = null;
    private Transaction transaction = null;
    public UserDaoHibernateImpl() {
        session = Util.getSessionFactory().openSession();

    }

    private static final String createUsersTableSql = "CREATE TABLE IF NOT EXISTS USERS (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255), lastName VARCHAR(255), age TINYINT)";
    private static final String dropUsersTableSql = "DROP TABLE IF EXISTS USERS";
    private static final String cleanUsersTableSql = "TRUNCATE TABLE USERS";

    @Override
    public void createUsersTable() {

        try {
            transaction = session.beginTransaction();
            session.createNativeMutationQuery(createUsersTableSql).executeUpdate();
            transaction.commit();
        } catch (Exception ignored) {
        }
    }

    @Override
    public void dropUsersTable() {

        try {
            transaction = session.beginTransaction();
            session.createNativeMutationQuery(dropUsersTableSql).executeUpdate();
            transaction.commit();
        } catch (Exception ignored) {
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);

        try {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
            System.out.printf("User с именем – %s добавлен в базу данных\n", name);
        } catch (Exception ignored) {
        }
    }

    @Override
    public void removeUserById(long id) {

        try {
            transaction = session.beginTransaction();
            session.remove(session.get(User.class, id));
            transaction.commit();
        } catch (Exception ignored) {
        }
    }

    @Override
    public List<User> getAllUsers() {

        TypedQuery<User> users = null;
        try {
            transaction = session.beginTransaction();
            users = session.createQuery("from User", User.class);
            transaction.commit();
            System.out.println(users);
        } catch (Exception ignored) {
        }
        return (List<User>) users;
    }

    @Override
    public void cleanUsersTable() {

        try {
            transaction = session.beginTransaction();
            session.createNativeMutationQuery(cleanUsersTableSql).executeUpdate();
            transaction.commit();
        } catch (Exception ignored) {
        }
    }
}
