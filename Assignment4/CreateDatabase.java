package Assignment4;

import java.sql.Connection;
import java.sql.DriverManager;

public class CreateDatabase {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:music_collection.db";

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                System.out.println("База данных создана: music_collection.db");
            }
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}
