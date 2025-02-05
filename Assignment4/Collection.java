package Assignment4;

import java.sql.*;

public class Collection {
    private String dbUrl = "jdbc:sqlite:music_collection.db";

    public static class MusicalMedia {
        private String name, author, genre;
        private int year;

        public MusicalMedia(String name, String author, String genre, int year) {
            this.name = name;
            this.author = author;
            this.genre = genre;
            this.year = year;
        }

        public String getName() { return name; }
        public String getAuthor() { return author; }
        public String getGenre() { return genre; }
        public int getYear() { return year; }
    }

    public static class MusicalComposition {
        private String name;
        private int duration;

        public MusicalComposition(String name, int duration) {
            this.name = name;
            this.duration = duration;
        }

        public String getName() { return name; }
        public int getDuration() { return duration; }
    }

    public boolean collectionExists(String collectionName) {
        String sql = "SELECT id FROM collections WHERE name = ?";
        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, collectionName);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (Exception e) {
            return false;
        }
    }

    public void createCollection(String collectionName, String ownerName) {
        String sql = "INSERT INTO collections(name, owner) VALUES(?, ?)";
        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, collectionName);
            pstmt.setString(2, ownerName);
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    public void addMedia(String collectionName, MusicalMedia media) {
        String sql = "INSERT INTO albums(name, author, genre, year, collection_id) VALUES(?, ?, ?, ?, (SELECT id FROM collections WHERE name = ?))";
        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, media.getName());
            pstmt.setString(2, media.getAuthor());
            pstmt.setString(3, media.getGenre());
            pstmt.setInt(4, media.getYear());
            pstmt.setString(5, collectionName);
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    public void addSong(String collectionName, String albumName, MusicalComposition song) {
        String sql = "INSERT INTO songs(album_id, name, duration) VALUES((SELECT id FROM albums WHERE name = ? AND collection_id = (SELECT id FROM collections WHERE name = ?)), ?, ?)";
        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, albumName);
            pstmt.setString(2, collectionName);
            pstmt.setString(3, song.getName());
            pstmt.setInt(4, song.getDuration());
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    public String getCollectionAsString(String collectionName) {
        StringBuilder collectionStr = new StringBuilder();
        String sqlAlbums = "SELECT * FROM albums WHERE collection_id = (SELECT id FROM collections WHERE name = ?)";
        String sqlSongs = "SELECT * FROM songs WHERE album_id = ?";

        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement pstmtAlbums = conn.prepareStatement(sqlAlbums)) {
            pstmtAlbums.setString(1, collectionName);
            ResultSet rsAlbums = pstmtAlbums.executeQuery();

            while (rsAlbums.next()) {
                int albumId = rsAlbums.getInt("id");
                collectionStr.append("Альбом: ").append(rsAlbums.getString("name"))
                        .append(" | Автор: ").append(rsAlbums.getString("author"))
                        .append(" | Жанр: ").append(rsAlbums.getString("genre"))
                        .append(" | Год: ").append(rsAlbums.getInt("year"))
                        .append("\n");

                try (PreparedStatement pstmtSongs = conn.prepareStatement(sqlSongs)) {
                    pstmtSongs.setInt(1, albumId);
                    ResultSet rsSongs = pstmtSongs.executeQuery();

                    while (rsSongs.next()) {
                        int duration = rsSongs.getInt("duration");
                        String formattedDuration = String.format("%d:%02d", duration / 60, duration % 60);

                        collectionStr.append("    Песня: ").append(rsSongs.getString("name"))
                                .append(" | Длительность: ").append(formattedDuration)
                                .append("\n");
                    }
                }
            }
        } catch (SQLException e) {
            return "Ошибка при получении коллекции: " + e.getMessage();
        }

        return collectionStr.length() > 0 ? collectionStr.toString() : "Коллекция пуста.";
    }
}
