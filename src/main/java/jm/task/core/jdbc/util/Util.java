package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Util {



    // реализуйте настройку соеденения с БД

    private static final String PASS_KEY = "db.password";
    private  static final String USERNAME_KEY = "db.username";
    private  static final String URL_KEY = "db.url";


 /*  private static final String DRIVER_KEY = "hibernate.driver_class";
   private static final String DIALECT_KEY = "hibernate.dialect";*/
    public static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

               /*configuration.setProperty("hibernate.connection.driver_class", DRIVER_KEY);
                configuration.setProperty("hibernate.connection.url", URL_KEY);
                configuration.setProperty("hibernate.connection.username", USERNAME_KEY);
                configuration.setProperty("hibernate.connection.password", PASS_KEY);
                configuration.setProperty("hibernate.dialect", DIALECT_KEY);
                configuration.setProperty("hibernate.show_sql", "true");
                configuration.setProperty("hibernate.hbm2ddl", "drop");
                configuration.setProperty("hibernate.current_session_context_class", "thread");*/

                configuration.addAnnotatedClass(User.class);

                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());

            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }

        return sessionFactory;
    }

    private Util(){
    }

    static {
        try {
            LoadDriver();// для работы в старых версиях java
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public static Connection open () throws SQLException {
            return DriverManager.getConnection(PropertiesUtil.get(URL_KEY),
                    PropertiesUtil.get(USERNAME_KEY),
                    PropertiesUtil.get(PASS_KEY));

    }

    private static void LoadDriver() throws ClassNotFoundException {

        Class.forName("org.postgresql.Driver");
    }



}
