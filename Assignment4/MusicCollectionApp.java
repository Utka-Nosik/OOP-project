/***************************************************************************************
 **    ######           #         ##    ##       ####                ##   #####        **
 **    ##    ##        ###        ##   ##      ##    ##            ##    ##   ##       **
 **    ##    ##       ## ##       ##  ##      ##      ##         ##           ##       **
 **    ######        ##   ##      #####      ##        ##      ##           ##         **
 **    ##    ##     #########     ##  ##      ##      ##         ##           ##       **
 **    ##    ##    ##       ##    ##   ##      ##    ##            ##    ##   ##       **
 **    ######     ##         ##   ##    ##       ####                ##   #####        **
 ***************************************************************************************/

package Assignment4;

import java.util.*;

class Collection {
    private String name;
    private String owner;
    private List<MusicalMedia> media;

    public Collection(String name, String owner) {
        this.name = validateInput(name, "Enter a valid collection name: ");
        this.owner = validateOwner(owner);
        this.media = new ArrayList<>();
    }

    private String validateInput(String input, String errorMessage) {
        Scanner scanner = new Scanner(System.in);
        while (input.trim().isEmpty()) {
            System.out.print(errorMessage);
            input = scanner.nextLine();
        }
        return input;
    }

    private String validateOwner(String owner) {
        Scanner scanner = new Scanner(System.in);
        while (!owner.matches("[a-zA-Z]+")) {
            System.out.print("Enter a valid owner's name (letters only): ");
            owner = scanner.nextLine();
        }
        return owner;
    }

    public void addMedia(MusicalMedia media) {
        this.media.add(media);
    }

    public void removeMedia(String mediaName) {
        media.removeIf(m -> m.getName().equalsIgnoreCase(mediaName));
    }

    public String searchSong(String title) {
        for (MusicalMedia media : this.media) {
            for (MusicalComposition song : media.getMusicalWorks()) {
                if (song.getName().equalsIgnoreCase(title)) {
                    return "Song '" + song.getName() + "' found in album '" + media.getName() + "'.";
                }
            }
        }
        return "Song not found.";
    }

    public void displayCollection() {
        System.out.println("Collection: " + name + " | Owner: " + owner);
        for (MusicalMedia media : this.media) {
            media.displayMedia();
        }
    }

    public List<MusicalMedia> getMedia() {
        return media;
    }
}

class MusicalMedia {
    private String name;
    private String author;
    private String genre;
    private int year;
    private List<MusicalComposition> musicalWorks;

    public MusicalMedia(String name, String author, String genre, int year) {
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.year = year;
        this.musicalWorks = new ArrayList<>();
    }

    public void addSong(MusicalComposition song) {
        this.musicalWorks.add(song);
    }

    public void removeSong(String songName) {
        musicalWorks.removeIf(s -> s.getName().equalsIgnoreCase(songName));
    }

    public String getName() {
        return name;
    }

    public List<MusicalComposition> getMusicalWorks() {
        return musicalWorks;
    }

    public void displayMedia() {
        System.out.println("Album: " + name + " | Author: " + author + " | Genre: " + genre + " | Year: " + year);
        for (MusicalComposition song : musicalWorks) {
            System.out.println("    Song: " + song.getName() + " | Duration: " + song.getDuration() + " min");
        }
    }
}

class MusicalComposition {
    private String name;
    private int duration;

    public MusicalComposition(String name, int duration) {
        this.name = name;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }
}

public class MusicCollectionApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter collection name: ");
        String collectionName = scanner.nextLine();
        System.out.print("Enter owner name: ");
        String ownerName = scanner.nextLine();
        Collection collection = new Collection(collectionName, ownerName);

        while (true) {
            System.out.println("\n1. Add Album\n2. Remove Album\n3. Add Song\n4. Remove Song\n5. Search Song\n6. Display Collection\n7. Exit");
            System.out.print("Choose an action: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter album name: ");
                    String albumName = scanner.nextLine();
                    System.out.print("Enter author: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter genre: ");
                    String genre = scanner.nextLine();
                    System.out.print("Enter year: ");
                    int year = Integer.parseInt(scanner.nextLine());
                    collection.addMedia(new MusicalMedia(albumName, author, genre, year));
                    System.out.println("Album has added successfully");
                    break;
                case "2":
                    System.out.print("Enter album name to remove: ");
                    String removeAlbum = scanner.nextLine();
                    collection.removeMedia(removeAlbum);
                    System.out.println("Album has removed");
                    break;
                case "3":
                    System.out.print("Enter album name to add song: ");
                    String albumToAddSong = scanner.nextLine();
                    for (MusicalMedia media : collection.getMedia()) {
                        if (media.getName().equalsIgnoreCase(albumToAddSong)) {
                            System.out.print("Enter song name: ");
                            String songName = scanner.nextLine();
                            System.out.print("Enter song duration: ");
                            int duration = Integer.parseInt(scanner.nextLine());
                            media.addSong(new MusicalComposition(songName, duration));
                        }
                    }
                    System.out.println("Song added successfully");
                    break;
                case "4":
                    System.out.print("Enter album name to remove song: ");
                    String albumToRemoveSong = scanner.nextLine();
                    for (MusicalMedia media : collection.getMedia()) {
                        if (media.getName().equalsIgnoreCase(albumToRemoveSong)) {
                            System.out.print("Enter song name: ");
                            String songToRemove = scanner.nextLine();
                            media.removeSong(songToRemove);
                        }
                    }
                    break;
                case "5":
                    System.out.print("Enter song name to search: ");
                    String searchSong = scanner.nextLine();
                    System.out.println(collection.searchSong(searchSong));
                    break;
                case "6":
                    collection.displayCollection();
                    break;
                case "7":
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Enter a valid choice!");
            }
        }
    }
}

