package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Алексей", "Сидоров", (byte) 25);
        userService.removeUserById(3);
        userService.saveUser("Виктор", "Прокопьев", (byte) 29);
        userService.saveUser("Алишер", "Морозов", (byte) 34);
        userService.saveUser("Евгений", "Гальцев", (byte) 18);
        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();
        userService.dropUsersTable();
        // реализуйте алгоритм здесь
    }
}
