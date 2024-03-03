package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {



    // реализуйте настройку соеденения с БД

    private static final String PASS_KEY = "db.password";
    private  static final String USERNAME_KEY = "db.username";
    private  static final String URL_KEY = "db.url";

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
