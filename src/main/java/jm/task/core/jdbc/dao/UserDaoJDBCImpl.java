package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl(com.mysql.cj.util.Util util) {

    }

    public void createUsersTable() {


        try ( Connection connection = Util.open();
                Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users " +
                    "( id SERIAL PRIMARY KEY, ferstname varchar(50), lastname varchar(50), age int\n)");
        } catch (SQLException e) {
            System.out.println("Создание таблицы пошло не по плану");
        }
    }

    public void dropUsersTable() {

        try (Connection connection = Util.open();
                Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE users");
        } catch (SQLException e) {
            System.out.println("Удаление таблицы пошло не по плану");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.open();
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (ferstname, lastname, age) VALUES (?,?,?)")){
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.printf("User с именем %s добавлен в таблицу\n", name);
        } catch (SQLException e) {
            System.out.println("Сохранение пользователя пошло не по плану");
        }

    }

    public void removeUserById(long id) {
        try (Connection connection = Util.open();
                PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users WHERE id=?")){

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Удаление пользователя по id пошло не по плану");
        }
    }

    public List<User> getAllUsers() {
        List <User> users = new ArrayList<>();

        try (Connection connection = Util.open();
                ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM users")){
            while (resultSet.next()){
                User user = new User(
                        resultSet.getString("ferstname"),
                        resultSet.getString("lastname"),
                        resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                users.add(user);
            }
            for (User usr: users){
                System.out.println(usr);
            }
        } catch (SQLException e) {
            System.out.println("Получение всех пользователей пошло не по плану");
        }

        return users;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.open();
                Statement statement = connection.createStatement()){
            statement.executeUpdate("TRUNCATE TABLE users");

        } catch (SQLException e) {
            System.out.println("Зачистка пользователей пошла не по плану");
        }

    }
}
