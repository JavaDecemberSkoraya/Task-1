package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import static jm.task.core.jdbc.util.Util.sessionFactory;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()){
            transaction = session.beginTransaction();

            String createUsr = "CREATE TABLE IF NOT EXISTS users ("+
                    """
                            id SERIAL PRIMARY KEY,
                                                 ferstname VARCHAR(255),
                                                 lastname VARCHAR(255),
                                                 age SMALLINT)
                            """;

            session.createNativeQuery(createUsr).executeUpdate();
            transaction.commit();
        }
        catch (HibernateError e){
            if (transaction != null && transaction.isActive()){
                transaction.rollback();
            }
            e.printStackTrace();
        }

    }

    @Override
    public void dropUsersTable() {

        Transaction transaction = null;

        try (Session session = Util.getSessionFactory().openSession()){
            transaction = session.beginTransaction();

            session.createNativeQuery("DROP TABLE IF EXISTS users").executeUpdate();
            transaction.commit();

        }
        catch (HibernateError e){
            if (transaction != null && transaction.isActive()){
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;

        try (Session session = Util.getSessionFactory().openSession()){
            User user = new User(name, lastName, age);

            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();

            System.out.printf("User с именем %s добавлен в таблицу\n", name);

        }
        catch (HibernateError e){
            if (transaction != null && transaction.isActive()){
                transaction.rollback();
            }
            e.printStackTrace();
        }

    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session =Util.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            User userdrop = session.find(User.class, id);
            if (userdrop != null){
                session.remove(userdrop);
            }
        }
        catch (HibernateError e){
            if (transaction != null && transaction.isActive()){
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List <User> list = new ArrayList<>();

        try (Session session = Util.getSessionFactory().openSession()){
            Query<User> queue = session.createQuery("FROM User", User.class);
            list=queue.list();
        }
        catch (HibernateError e){
            e.printStackTrace();
        }
        return  list;
    }

    @Override
    public void cleanUsersTable() {

        Transaction transaction = null;

        try (Session session = Util.getSessionFactory().openSession()){
            transaction = session.beginTransaction();

            Query queue = session.createQuery("DELETE from User");
            queue.executeUpdate();

            transaction.commit();
        }
        catch (HibernateError e){
            if (transaction != null && transaction.isActive()){
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
