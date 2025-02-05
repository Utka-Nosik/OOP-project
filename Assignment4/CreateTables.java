package Assignment4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class CreateTables {

    public static void main(String[] args) {
        String url = "jdbc:sqlite:music_collection.db";

        String createCollectionsTable = """
                CREATE TABLE IF NOT EXISTS collections (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT UNIQUE NOT NULL,
                    owner TEXT NOT NULL
                );
                """;

        String createAlbumsTable = """
                CREATE TABLE IF NOT EXISTS albums (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    author TEXT NOT NULL,
                    genre TEXT NOT NULL,
                    year INTEGER NOT NULL,
                    collection_id INTEGER NOT NULL,
                    FOREIGN KEY (collection_id) REFERENCES collections (id) ON DELETE CASCADE
                );
                """;

        String createSongsTable = """
                CREATE TABLE IF NOT EXISTS songs (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    album_id INTEGER NOT NULL,
                    name TEXT NOT NULL,
                    duration INTEGER NOT NULL,
                    FOREIGN KEY (album_id) REFERENCES albums (id) ON DELETE CASCADE
                );
                """;

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {

            stmt.execute(createCollectionsTable);
            stmt.execute(createAlbumsTable);
            stmt.execute(createSongsTable);

            System.out.println("Таблицы успешно созданы!");
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}
