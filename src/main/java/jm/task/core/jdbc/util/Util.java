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
        LoadDriver();// для работы в старых версиях java
    }


    public static Connection open (){
        try {
            return DriverManager.getConnection(PropertiesUtil.get(URL_KEY),
                    PropertiesUtil.get(USERNAME_KEY),
                    PropertiesUtil.get(PASS_KEY));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void LoadDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



}
