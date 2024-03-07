package jm.task.core.jdbc;


import com.mysql.cj.jdbc.ConnectionGroupManager;
import com.mysql.cj.util.Util;
import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.*;
import java.util.List;


public class Main {
    public static void main(String[] args) {

        UserService userService = new UserServiceImpl();




        userService.createUsersTable();
        userService.saveUser("Гарри", "Поттер", (byte) 17);
        userService.saveUser("Гермиона", "Грейнджер", (byte) 17);
        userService.saveUser("Рон", "Уизли", (byte) 17);
        userService.saveUser("Альбус Персиваль Вульфрик Брайан", "Дамблдор", (byte) 133);
        userService.removeUserById((long) 4);
        userService.getAllUsers().forEach(System.out::println);
        userService.cleanUsersTable();
        userService.dropUsersTable();






    }


}
