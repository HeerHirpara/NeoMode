import java.io.*;
import java.util.*;

 class NeoMoodApp {
    static HashMap<String, List<String>> moodMap = new HashMap<>();
    static List<String> allSongs = Arrays.asList(
            "Believer - Imagine Dragons",
            "Shape of You - Ed Sheeran",
            "Faded - Alan Walker",
            "Senorita - Shawn Mendes",
            "Fix You - Coldplay",
            "Someone Like You - Adele",
            "Eye of the Tiger - Survivor",
            "Stronger - Kanye West",
            "Sunset Lover - Petit Biscuit"
    );
    static List<String> playlist = new ArrayList<>();
    static String userName = "";

    public static void main(String[] args) throws IOException {
        initMoodMap();
        loadPlaylistFromFile();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter your name: ");
        userName = reader.readLine().trim();
        System.out.println("Welcome, " + userName + "!");

        boolean exit = false;

        while (!exit) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Mood-based music suggestion");
            System.out.println("2. Free browse & playlist");
            System.out.println("3. View playlist stats");
            System.out.println("4. Exit");
            System.out.print("Choose: ");
            String choice = reader.readLine();

            switch (choice) {
                case "1":
                    suggestByMood(reader);
                    break;
                case "2":
                    freeBrowseMenu(reader);
                    break;
                case "3":
                    showPlaylistStats();
                    break;
                case "4":
                    savePlaylistToFile();
                    exit = true;
                    System.out.println("Goodbye, " + userName + "!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void initMoodMap() {
        moodMap.put("happy", Arrays.asList("Happy - Pharrell Williams", "Best Day of My Life - American Authors"));
        moodMap.put("sad", Arrays.asList("Fix You - Coldplay", "Someone Like You - Adele"));
        moodMap.put("energetic", Arrays.asList("Eye of the Tiger - Survivor", "Stronger - Kanye West"));
        moodMap.put("relax", Arrays.asList("Sunset Lover - Petit Biscuit", "Weightless - Marconi Union"));
    }

    private static void suggestByMood(BufferedReader reader) throws IOException {
        System.out.print("Enter your mood (happy/sad/energetic/relax): ");
        String mood = reader.readLine().toLowerCase();
        List<String> songs = moodMap.get(mood);
        if (songs != null) {
            System.out.println("Songs for mood '" + mood + "':");
            for (String song : songs) {
                System.out.println("- " + song);
            }
        } else {
            System.out.println("No songs found for this mood.");
        }
    }

    private static void freeBrowseMenu(BufferedReader reader) throws IOException {
        boolean back = false;
        while (!back) {
            System.out.println("\nBrowse Menu:");
            System.out.println("1. Show all songs");
            System.out.println("2. Add song to playlist");
            System.out.println("3. Remove song from playlist");
            System.out.println("4. View playlist");
            System.out.println("5. Shuffle playlist");
            System.out.println("6. Back to main menu");
            System.out.print("Choose: ");
            String choice = reader.readLine();

            switch (choice) {
                case "1":
                    showAllSongs();
                    break;
                case "2":
                    addSongToPlaylist(reader);
                    break;
                case "3":
                    removeSongFromPlaylist(reader);
                    break;
                case "4":
                    viewPlaylist();
                    break;
                case "5":
                    shufflePlaylist();
                    break;
                case "6":
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void showAllSongs() {
        for (int i = 0; i < allSongs.size(); i++) {
            System.out.println((i + 1) + ". " + allSongs.get(i));
        }
    }

    private static void addSongToPlaylist(BufferedReader reader) throws IOException {
        showAllSongs();
        System.out.print("Enter song number to add: ");
        try {
            int num = Integer.parseInt(reader.readLine());
            if (num >= 1 && num <= allSongs.size()) {
                String song = allSongs.get(num - 1);
                playlist.add(song);
                System.out.println("Added: " + song);
            } else {
                System.out.println("Invalid number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }

    private static void removeSongFromPlaylist(BufferedReader reader) throws IOException {
        if (playlist.isEmpty()) {
            System.out.println("Playlist is empty.");
            return;
        }
        viewPlaylist();
        System.out.print("Enter song number to remove: ");
        try {
            int num = Integer.parseInt(reader.readLine());
            if (num >= 1 && num <= playlist.size()) {
                String removed = playlist.remove(num - 1);
                System.out.println("Removed: " + removed);
            } else {
                System.out.println("Invalid number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }

    private static void viewPlaylist() {
        if (playlist.isEmpty()) {
            System.out.println("Playlist is empty.");
        } else {
            for (int i = 0; i < playlist.size(); i++) {
                System.out.println((i + 1) + ". " + playlist.get(i));
            }
        }
    }

    private static void shufflePlaylist() {
        if (playlist.isEmpty()) {
            System.out.println("Playlist is empty.");
        } else {
            Collections.shuffle(playlist);
            System.out.println("Playlist shuffled.");
        }
    }

    private static void showPlaylistStats() {
        int totalSongs = playlist.size();
        int avgSongDuration = 3; // in minutes, hardcoded
        int totalDuration = totalSongs * avgSongDuration;
        System.out.println("Total songs in playlist: " + totalSongs);
        System.out.println("Estimated total duration: " + totalDuration + " minutes");
    }

    private static void savePlaylistToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("playlist.txt"))) {
            for (String song : playlist) {
                writer.write(song);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving playlist: " + e.getMessage());
        }
    }

    private static void loadPlaylistFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("playlist.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                playlist.add(line);
            }
        } catch (IOException e) {
            // Ignore if file doesn't exist
        }
    }
}
