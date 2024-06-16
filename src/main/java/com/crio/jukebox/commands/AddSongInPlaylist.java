package com.crio.jukebox.commands;

import java.util.List;
import com.crio.jukebox.entities.Playlist;
import com.crio.jukebox.services.IPlaylistService;

public class AddSongInPlaylist implements ICommand{

    private final IPlaylistService playlistService;

    public AddSongInPlaylist(IPlaylistService playlistService){
        this.playlistService = playlistService;
    }

    @Override
    public void execute(List<String> tokens) {
        String userId = tokens.get(2);
        String playlistId = tokens.get(3);    
        if(tokens.get(1).equals("ADD-SONG")){
            Playlist playlist = playlistService.addSongInPlaylist(userId, playlistId, tokens);
            System.out.println("Playlist ID - " + playlist.getId());
            System.out.println(("Playlist Name - " + playlist.getPlaylistName()));
            StringBuilder stringBuilder = new StringBuilder();
            for (String id : playlist.getSongIds()) {
                stringBuilder.append(" ").append(id);
            }
            System.out.println("Song IDs -" + stringBuilder);        
        }
        else{
            Playlist playList = playlistService.deleteSongInPlaylist(userId, playlistId, tokens);
            System.out.println("Playlist ID - " + playList.getId());
            System.out.println(("Playlist Name - " + playList.getPlaylistName()));
            StringBuilder stringBuilder = new StringBuilder();
            for (String id : playList.getSongIds()) {
                stringBuilder.append(" ").append(id);
            }
            // if 
            System.out.println("Song IDs -" + stringBuilder);        
        }
        
    }
    
}
