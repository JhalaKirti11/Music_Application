package com.jukebox.app.MusicApp;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;
import java.util.ArrayList;

public class MusicAudio {
    public static void playSound(String filePath) {
        try {
            File path = new File(filePath);
            AudioInputStream ais = AudioSystem.getAudioInputStream(path);
            Clip clip = AudioSystem.getClip(); // throws LineUnvailableException;
            clip.open(ais);
            clip.start();

                // music on loop continuously...
            clip.loop(Clip.LOOP_CONTINUOUSLY);

                // to stop music: show an OK button:
            JOptionPane.showMessageDialog(null, "Press OK to stop audio");
            clip.stop();

        }
        catch(LineUnavailableException e){
            System.out.println("Can't Play The Song!");
        }
        catch(UnsupportedAudioFileException e){
            System.out.println("Can't Play The Song!");
        }
        catch (Exception e) {
            System.out.println("Can't Play The Song!");
        }
    }


    public static void playListSongs(ArrayList<String> i){
        for(String filePath : i){
        try{
            File path = new File(filePath);
            AudioInputStream ais = AudioSystem.getAudioInputStream(path);
            Clip clip = AudioSystem.getClip(); // throws LineUnvailableException;
            clip.open(ais);
            clip.start();

            // to stop music: show an OK button:
            JOptionPane.showMessageDialog(null, "Press OK to stop audio");
                 clip.stop();
        
        }catch(Exception e){
            System.out.println("Can't play the song!");
        }
    }
    }
}
