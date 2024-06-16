package com.crio.jukebox.commands;

import java.util.List;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.services.IPlaylistService;

public class PlayPlaylistCommand implements ICommand {

    private final IPlaylistService playlistservice;

    public PlayPlaylistCommand (IPlaylistService playlistService){
        this.playlistservice = playlistService;
    }

    @Override
    public void execute(List<String> tokens) {
        String userID = tokens.get(1);
        String playlistID = tokens.get(2);

        Song song = playlistservice.playSong(userID, playlistID);
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
