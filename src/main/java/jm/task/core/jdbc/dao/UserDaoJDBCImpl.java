package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        final String CREATEUSERSTABLE = "CREATE TABLE `sidelnikov_DB`.`new_table_test` (" +
                "  `id` INT NOT NULL AUTO_INCREMENT," +
                "  `name` VARCHAR(45) NOT NULL," +
                "  `lastName` VARCHAR(45) NOT NULL," +
                "  `age` INT NULL," +
                "  PRIMARY KEY (`id`)," +
                "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);";
        Connection connection = Util.connectionDB();
        if (connection != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(CREATEUSERSTABLE)) {
                preparedStatement.execute();
                System.out.println("Таблица успешно создана!");
            } catch (SQLSyntaxErrorException s) {
                Util.print("Такая таблица уже существует!");
                s.printStackTrace();
            } catch (SQLException e) {
                Util.print("Возникла ошибка при создании таблицы!");
                e.printStackTrace();
            } finally {
                try {
                    Util.disconnectDB();
                } catch (SQLException e) {
                    Util.print("Произошла ошибка при закрытии соединения!");
                    e.printStackTrace();
                }
            }
        } else {
            Util.print("Не удалось установить соединение с базой данных!");
        }
    }

    public void dropUsersTable() {
        final String DROPUSERSTABLE = "drop table new_table_test;";
        Connection connection = Util.connectionDB();
        if (connection != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(DROPUSERSTABLE)) {
                preparedStatement.execute();
                Util.print("Таблица успешно удалена!");
            } catch (SQLException e) {
                Util.print("Таблицы не существует!");
                e.printStackTrace();
            } finally {
                try {
                    Util.disconnectDB();
                } catch (SQLException e) {
                    Util.print("Произошла ошибка при закрытии соединения!");
                    throw new RuntimeException(e);
                }
            }
        } else {
            Util.print("Не удалось установить соединение с базой данных!");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        final String SAVEUSER = "insert into new_table_test (name, lastName, age) values(?, ?, ?);";
        Connection connection = Util.connectionDB();
        if (connection != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SAVEUSER)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, lastName);
                preparedStatement.setInt(3, age);
                preparedStatement.executeUpdate();
                Util.print("User с именем - " + name + " добавлен в базу данных");
            } catch (SQLException e) {
                Util.print("Возникла ошибка при добалении пользователя!");
                e.printStackTrace();
            } finally {
                try {
                    Util.disconnectDB();
                } catch (SQLException e) {
                    Util.print("Произошла ошибка при закрытии соединения!");
                    e.printStackTrace();
                }
            }
        } else {
            Util.print("Не удалось установить соединение с базой данных!");
        }
    }

    public void removeUserById(long id) {
        final String REMOVEUSERBYID = "delete from new_table_test where id = " + id;
        Connection connection = Util.connectionDB();
        if (connection != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(REMOVEUSERBYID)) {
                preparedStatement.execute();
                Util.print("Пользователь успешно удален!"); // Нужно обработать тот случай, когда нет указанного id в бд
            } catch (SQLException e) {
                Util.print("Пользователя с таким id не существует!");
                e.printStackTrace();
            } finally {
                try {
                    Util.disconnectDB();
                } catch (SQLException e) {
                    Util.print("Произошла ошибка при закрытии соединения!");
                    e.printStackTrace();
                }
            }
        } else {
            Util.print("Не удалось установить соединение с базой данных!");
        }
    }

    public List<User> getAllUsers() {
        final String GETALLUSERS = "select * from new_table_test;";
        Connection connection = Util.connectionDB();
        List<User> userList = new ArrayList<>();
        if (connection != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(GETALLUSERS)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String lastName = resultSet.getString("lastName");
                    byte age = (byte) resultSet.getInt("age");
                    User user = new User(name, lastName, age);
                    user.setId((long) id);
                    userList.add(user);
                }
                for (User user : userList) {
                    System.out.println(user);
                }
                return userList;
            } catch (SQLException e) {
                Util.print("Произошла ошибка при получении данных из таблицы");
                throw new RuntimeException(e);
            } finally {
                try {
                    Util.disconnectDB();
                } catch (SQLException e) {
                    Util.print("Произошла ошибка при закрытии соединения!");
                    throw new RuntimeException(e);
                }
            }
        } else {
            Util.print("Не удалось установить соединение с базой данных!");
        }
        return userList;
    }

    public void cleanUsersTable() {
        final String CLEANUSERTABLE = "delete from new_table_test;";
        Connection connection = Util.connectionDB();
        if (connection != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(CLEANUSERTABLE)) {
                preparedStatement.execute();
                Util.print("Таблица успешно очищена!");
            } catch (SQLException e) {
                Util.print("Произошла ошибка при удалении данных из таблицы");
                throw new RuntimeException(e);
            } finally {
                try {
                    Util.disconnectDB();
                } catch (SQLException e) {
                    Util.print("Произошла ошибка при закрытии соединения!");
                    throw new RuntimeException(e);
                }
            }
        } else {
            Util.print("Не удалось установить соединение с базой данных!");
        }
    }
}
