package com.jukebox.app.MusicApp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.ArrayList;

public class Album{
     Connection con = ConnectionClassForDM.getConnection();
     Scanner sc;

    public void viewAllAlbum(){
         try {
            System.out.println();
            String sql = "select AlbumName from Album";
            PreparedStatement stat = con.prepareStatement(sql);
            System.err.println("\nAlbums :");
           
            ResultSet rs = stat.executeQuery();
            while(rs.next()){
                System.out.println("  "+rs.getString("AlbumName"));
            }
    }catch(Exception e){
        System.out.println("Album Not Found!");
    }
}

public ArrayList<String> viewAlbumSongs(){
    sc = new Scanner(System.in);
     System.out.print("Enter Album name: ");
     ArrayList<String> songList = new ArrayList<>();
        String albumName = sc.nextLine();
        boolean albumFound = false;
        boolean albumSong = false;

        try {
 String qur ="SELECT Album.AlbumName, allsongs.SongID, allsongs.Title, allsongs.Artist, allsongs.Path FROM Album INNER JOIN AlbumSongs ON Album.AlbumID=AlbumSongs.AlbumID INNER JOIN allsongs ON AlbumSongs.SongID = allsongs.SongID";
            PreparedStatement statement = con.prepareStatement(qur);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                if(albumName.equals(rs.getString("AlbumName"))){
                    albumFound = true;
                    albumSong = false;
                String title = rs.getString("Title");
                String artist = rs.getString("Artist");
                songList.add(rs.getString("Path"));
            
                System.out.println("\n***Title: " + title +" | Artist: " + artist+"***");
            }
        }
            if(!albumFound){
                System.out.println("\n---Album doesn't found Or Has No Song!---");
            }
           
                return songList;
        } catch (SQLException e) {
            System.out.println("Error fetching songs.");
            //e.printStackTrace();
                return null;
        }
    }

    public void playAlbumSong(){
        ArrayList<String> path = viewAlbumSongs();
            MusicAudio.playListSongs(path);
    }
}
