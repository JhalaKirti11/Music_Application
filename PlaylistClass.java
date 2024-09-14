package com.jukebox.app.MusicApp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.ArrayList;

class Playlist {
    Scanner scanner = new Scanner(System.in);
    Connection connection;
    PreparedStatement statement;
    
    User loggedInUser;
    boolean loggedIn = true;

    // Constructor to initialize the connection and user
    public Playlist(User loggedInUser) {
        this.connection = ConnectionClassForDM.getConnection(); // Get connection from the connection class
        this.loggedInUser = loggedInUser; // Pass the logged-in user
    }

    public void userMenu() {
        while (loggedIn) {
            System.out.println("\nUser Menu:");
            System.out.println("1. Play A Song");
            System.out.println("2. Create Playlist");
            System.out.println("3. View All Playlists");
            System.out.println("4. Add Song to Playlist");
            System.out.println("5. View Songs in Playlist");
            System.out.println("6. Play Song From Playlist");
            System.out.println("7. View All Album");
            System.out.println("8. View Album Songs");
            System.out.println("9. Play Album Songs");
            System.out.println("10. Logout");

            try{
            int choice = MainApp.getIntegerInput(scanner, "Please Enter the number: ");
            Album ad = new Album();

        switch (choice) {
                
            case 1: 
                try{
                    System.out.println();
                    String qry = "Select SongID, Title, Path from AllSongs";
                    statement = connection.prepareStatement(qry);
                    ResultSet rs = statement.executeQuery();
                    while(rs.next()){
                        System.out.println(rs.getString("Title"));
                    }
                        System.out.print("Select a song name from the following song list: ");
                            String name = scanner.nextLine();
                            PlaySong.playSong(name);
                          //  PlaySong.showSongMenu();
                }catch(Exception e){
                    System.out.println("Couldn't find the songs!");
                }
                break;

            case 2:
                    createPlaylist();
                    break;
            case 3:
                    viewPlaylists();
                    break;
            case 4:
                    addSongToPlaylist();
                    break;
            case 5:
                    viewSongsInPlaylist();
                    break;
            case 6:
                    playSongInPlaylist();
                    break;
            case 7:
                    ad.viewAllAlbum();
                    break;
            case 8:
                    ad.viewAlbumSongs();
                    break;
            case 9:
                    ad.playAlbumSong();
                    break;
            case 10:
                    loggedIn = false;
                     System.out.println("Logged out successfully.");
                     System.out.println("==============================================");
                     System.out.println("--------------*THANK YOU*--------------");
                    break;
            default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }catch(Exception e){
               System.out.println("enter valid number");
        }
    }
}

    private void createPlaylist() {
        System.out.print("Enter playlist name: ");
        String playlistName = scanner.nextLine();

        try {
            // Corrected table and column names based on the database schema
            String sql = "INSERT INTO Playlists (PlaylistName, UserID) VALUES (?, ?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, playlistName);
            statement.setInt(2, loggedInUser.getUserID()); // Use the logged-in user's ID
            statement.executeUpdate();
            //System.out.println("Playlist created successfully!");
        } catch (SQLException e) {
            System.out.println("Error creating playlist.");
            //e.printStackTrace();
        }
    }


    private void viewPlaylists() {
        try {
            // Corrected table and column names based on the database schema
            String sql = "SELECT * FROM Playlists WHERE UserID = ?";
             statement = connection.prepareStatement(sql);
            statement.setInt(1, loggedInUser.getUserID()); // Use the logged-in user's ID
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int playlistId = resultSet.getInt("PlaylistID");
                String name = resultSet.getString("PlaylistName");
                System.out.println("Playlist ID: " + playlistId + ", Name: " + name);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching playlists.");
            //e.printStackTrace();
        }
    }



    private void addSongToPlaylist() {          // add song to exsisting playlist: 
        System.out.print("Enter playlist Name: ");
                String playlistName = scanner.nextLine();
        System.out.print("Enter song name: ");
                String title = scanner.nextLine();
                int songId =0;
                int playlistID=0;
                 boolean foundPlaylist = false;
                 boolean songFound = false;
                
            try {
            // get PlaylistID from playlist table:
                String qur = "select PlaylistID from Playlists where playlistName ='"+playlistName+"'";
                 statement = connection.prepareStatement(qur);
                ResultSet rs = statement.executeQuery();
                while(rs.next()){
                 
                     playlistID = rs.getInt("PlaylistID");
                     foundPlaylist = true;
                }
              

            // get songID from song table:
                String qur1 = "select SongID from allsongs where Title= '"+title+"'";
                statement = connection.prepareStatement(qur1);
                rs = statement.executeQuery();
                while(rs.next()){
                     songId = rs.getInt("SongID");
                     songFound = true;
                }
                if(!foundPlaylist){
                    System.out.println("\n---Playlist doesn't Found---");
                }else if(!songFound){
                    System.out.println("\n---Song Doesn't Found---");
                }

            String sql = "INSERT INTO Playlistsongs (PlaylistID, SongID) VALUES (?, ?)";
                statement = connection.prepareStatement(sql);
                statement.setInt(1, playlistID);
                statement.setInt(2, songId);
                statement.executeUpdate();
            System.out.println("Song added to playlist successfully!");
        
            }catch (SQLException e) {
          //  System.out.println("\n---Playlist/Song Doesn't Found.---");
            //e.printStackTrace();
        }
    }


    private ArrayList<String> viewSongsInPlaylist() {
        System.out.print("\nEnter Playlist Name: ");
        String playlistName = scanner.nextLine();
        boolean foundPlaylist = false;
        boolean playlistEmpty = true;
        
        ArrayList<String> songList = new ArrayList<>();
        try {
            String q = "select*from playlists";
            statement = connection.prepareStatement(q);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                if((rs.getString("playlistName")).equals(playlistName)){
                    foundPlaylist = true;
                }
            }
String qur ="SELECT Playlists.PlaylistName, AllSongs.SongID, AllSongs.Title, AllSongs.Artist, AllSongs.Path FROM Playlists INNER JOIN PlaylistSongs ON Playlists.PlaylistId = Playlistsongs.PlaylistID INNER JOIN AllSongs ON PlaylistSongs.SongID = AllSongs.SongID";
            statement = connection.prepareStatement(qur);
             rs = statement.executeQuery();
            while (rs.next()) {
               
              if(playlistName.equals(rs.getString("PlaylistName"))){
                   
                    playlistEmpty = false;
                int songId = rs.getInt("SongID");
                String title = rs.getString("Title");
                String artist = rs.getString("Artist");
                songList.add(rs.getString("Path"));
            
                System.out.println("*** Song ID: " + songId +" | Title: " + title +" | Artist: " + artist+" ***");
            }
        }
            if(!foundPlaylist){
                System.out.println("Playlist doesn't exist");
            }else if (playlistEmpty) {
            System.out.println("Playlist Is Empty.");
        }
    
            return songList;
        } catch (SQLException e) {
            System.out.println("Error In Fetching Songs.");
            return null;
        }
    }

    private void playSongInPlaylist() {
        ArrayList<String> path = viewSongsInPlaylist();
            
            MusicAudio.playListSongs(path);
    }
}
