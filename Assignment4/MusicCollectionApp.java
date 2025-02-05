package Assignment4;

import java.util.Scanner;

import Assignment4.Collection;

public class MusicCollectionApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Collection collection = new Collection();

        System.out.print("Введите название музыкальной коллекции: ");
        String collectionName = scanner.nextLine();

        if (!collection.collectionExists(collectionName)) {
            System.out.print("Коллекция не найдена. Введите имя владельца для создания новой: ");
            String ownerName = scanner.nextLine();
            collection.createCollection(collectionName, ownerName);
        }

        while (true) {
            System.out.println("\nМеню:");
            System.out.println("1. Добавить альбом");
            System.out.println("2. Добавить песню");
            System.out.println("3. Показать коллекцию");
            System.out.println("4. Выйти");
            System.out.print("Выберите действие: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Введите название альбома: ");
                    String albumName = scanner.nextLine();
                    System.out.print("Введите автора: ");
                    String author = scanner.nextLine();
                    System.out.print("Введите жанр: ");
                    String genre = scanner.nextLine();
                    System.out.print("Введите год: ");
                    int year = Integer.parseInt(scanner.nextLine());
                    collection.addMedia(collectionName, new Collection.MusicalMedia(albumName, author, genre, year));
                    break;
                case "2":
                    System.out.print("Введите название альбома: ");
                    String albumForSong = scanner.nextLine();
                    System.out.print("Введите название песни: ");
                    String songName = scanner.nextLine();
                    System.out.print("Введите длительность песни (в секундах): ");
                    int duration = Integer.parseInt(scanner.nextLine());
                    collection.addSong(collectionName, albumForSong, new Collection.MusicalComposition(songName, duration));
                    break;
                case "3":
                    System.out.println(collection.getCollectionAsString(collectionName));
                    break;
                case "4":
                    System.out.println("Выход...");
                    return;
                default:
                    System.out.println("Неверный ввод. Попробуйте снова.");
            }
        }
    }
}
