package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Environment;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

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
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, "jdbc:mysql://localhost:3306/sidelnikov_DB");
                settings.put(Environment.USER, "root");
                settings.put(Environment.PASS, "root");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");

                // Optional settings
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                settings.put(Environment.HBM2DDL_AUTO, "update");

                configuration.setProperties(settings);

                configuration.addAnnotatedClass(User.class);

                sessionFactory = configuration.buildSessionFactory();

            } catch (Exception e) {
                e.printStackTrace();
                if (sessionFactory != null) {
                    sessionFactory.close();
                }
            }
        }
        return sessionFactory;
    }
}
// реализуйте настройку соеденения с БД
