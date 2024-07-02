package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    public static void print(String message) {
        System.out.println(message);
    }

    static Connection connection = null;

    public static Connection connectionDB() {
        try {
            connection = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/sidelnikov_DB", "root", "root");
            if (!connection.isClosed()) {
                print("Соединение с БД установлено!");
                return connection;
            }
        } catch (SQLException e) {
            print("Неуспешное подключение к БД!");
            e.printStackTrace();
        }
        return connection;
    }

    public static void disconnectDB() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            print("Отключен от БД!\n");
        } else if (connection == null || connection.isClosed()) {
            print("Отключение невозможно!");
        }
    }
}
    // реализуйте настройку соеденения с БД
