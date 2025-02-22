package Assignment4;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

import Assignment4.Collection;

public class MusicCollectionGUI {
    private Collection collection;
    private JFrame frame;
    private JTextField collectionNameField;
    private JTextArea outputArea;

    public MusicCollectionGUI() {
        collection = new Collection();
        frame = new JFrame("Музыкальная коллекция с бюджетом 2 пачки чипсов и слезы автора");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.getContentPane().setBackground(Color.BLACK);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));
        panel.setBackground(Color.BLACK);

        JLabel collectionLabel = new JLabel("Название коллекции:");
        collectionLabel.setForeground(Color.WHITE);
        collectionNameField = new JTextField();
        collectionNameField.setBackground(Color.DARK_GRAY);
        collectionNameField.setForeground(Color.WHITE);

        JButton createCollectionButton = createStyledButton("Создать коллекцию");
        JButton addAlbumButton = createStyledButton("Добавить альбом");
        JButton addSongButton = createStyledButton("Добавить песню");
        JButton showCollectionButton = createStyledButton("Показать коллекцию");

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setBackground(Color.BLACK);
        outputArea.setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        createCollectionButton.addActionListener(e -> createCollection());
        addAlbumButton.addActionListener(e -> addAlbumAction());
        addSongButton.addActionListener(e -> addSongAction());
        showCollectionButton.addActionListener(e -> showCollection());

        panel.add(collectionLabel);
        panel.add(collectionNameField);
        panel.add(createCollectionButton);
        panel.add(addAlbumButton);
        panel.add(addSongButton);
        panel.add(showCollectionButton);

        frame.add(panel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(30, 215, 96));
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        return button;
    }

    private void createCollection() {
        String collectionName = collectionNameField.getText();
        if (!collection.collectionExists(collectionName)) {
            String ownerName = JOptionPane.showInputDialog("Введите имя владельца коллекции:");
            if (ownerName != null && !ownerName.isEmpty()) {
                collection.createCollection(collectionName, ownerName);
                outputArea.append("Коллекция создана: " + collectionName + "\n");
            }
        } else {
            outputArea.append("Коллекция уже существует.\n");
        }
    }

    private void addAlbumAction() {
        String collectionName = collectionNameField.getText();
        if (!collection.collectionExists(collectionName)) {
            outputArea.append("Коллекция не найдена.\n");
            return;
        }

        String albumName = JOptionPane.showInputDialog("Введите название альбома:");
        String author = JOptionPane.showInputDialog("Введите автора альбома:");
        String genre = JOptionPane.showInputDialog("Введите жанр альбома:");
        int year = Integer.parseInt(JOptionPane.showInputDialog("Введите год выпуска:"));

        collection.addMedia(collectionName, new Collection.MusicalMedia(albumName, author, genre, year));
        outputArea.append("Альбом добавлен: " + albumName + "\n");
    }

    private void addSongAction() {
        String collectionName = collectionNameField.getText();
        if (!collection.collectionExists(collectionName)) {
            outputArea.append("Коллекция не найдена.\n");
            return;
        }

        String albumName = JOptionPane.showInputDialog("Введите название альбома:");
        String songName = JOptionPane.showInputDialog("Введите название песни:");
        int duration = Integer.parseInt(JOptionPane.showInputDialog("Введите длительность песни в секундах:"));

        collection.addSong(collectionName, albumName, new Collection.MusicalComposition(songName, duration));
        outputArea.append("Песня добавлена: " + songName + "\n");
    }

    private void showCollection() {
        String collectionName = collectionNameField.getText();
        outputArea.setText("");
        String collectionData = collection.getCollectionAsString(collectionName);
        outputArea.append(collectionData);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MusicCollectionGUI::new);
    }
}