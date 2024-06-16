package com.crio.jukebox.commands;

import java.util.List;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.entities.User;
import com.crio.jukebox.services.IPlaylistService;

public class PlaySongCommand implements ICommand {

    private final IPlaylistService playlistservice;

    public PlaySongCommand(IPlaylistService playlistService){
        this.playlistservice = playlistService;
    }

    @Override
    public void execute(List<String> tokens) {
        
        if(tokens.get(2).equals("NEXT")){
            String userID = tokens.get(1);
            User user = playlistservice.nextSong(userID);
            Song song = user.currentSong();
            System.out.println("Current Song Playing");
            System.out.print("Song - " + song.getSongName()+"\n");
            System.out.print("Album - " + song.getAlbumName()+"\n");
            StringBuilder stringBuilder = new StringBuilder();
            for (String artist : song.getFeaturedArtist()) {
                stringBuilder.append(artist).append(",");
            }

            if (stringBuilder.length() > 0) {
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            }

            System.out.print("Artists - " + stringBuilder.toString()+ "\n");
    
        }
        else if(tokens.get(2).equals("BACK")){
            String userID = tokens.get(1);
            User user = playlistservice.previousSong(userID);
            Song song = user.currentSong();
            System.out.print("Current Song Playing"+"\n");
            System.out.print("Song - " + song.getSongName()+"\n");
            System.out.print("Album - " + song.getAlbumName()+"\n");
            StringBuilder stringBuilder = new StringBuilder();
            for (String artist : song.getFeaturedArtist()) {
                stringBuilder.append(artist).append(",");
            }

            if (stringBuilder.length() > 0) {
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            }

            System.out.print("Artists - " + stringBuilder.toString()+ "\n");
        }
        else{

                String userID = tokens.get(1);
                String songID = tokens.get(2);
                User user = playlistservice.playSongFromId(userID, songID);
                if(user == null){
                    System.out.print("Given song id is not a part of the active playlist");
                }
                else{
                    Song song = user.currentSong();
                    System.out.print("Current Song Playing"+"\n");
                    System.out.print("Song - " + song.getSongName()+"\n");
                    System.out.print("Album - " + song.getAlbumName()+"\n");
                    StringBuilder stringBuilder = new StringBuilder();
                    for (String artist : song.getFeaturedArtist()) {
                        stringBuilder.append(artist).append(",");
                    }

                    if (stringBuilder.length() > 0) {
                        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                    }

                    System.out.print("Artists - " + stringBuilder.toString()+"\n");
                }
            
            
        }
    }

    
    
}
