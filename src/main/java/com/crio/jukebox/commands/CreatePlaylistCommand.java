package com.crio.jukebox.commands;

import java.util.List;
import com.crio.jukebox.entities.Playlist;
import com.crio.jukebox.exceptions.SongNotFoundException;
import com.crio.jukebox.exceptions.UserNotFoundException;
import com.crio.jukebox.services.IPlaylistService;
import com.crio.jukebox.services.ISongService;
import com.crio.jukebox.services.IUserService;

public class CreatePlaylistCommand implements ICommand{
    
    private final IUserService userService;
    private final IPlaylistService playlistService;
    private final ISongService songService;

    public CreatePlaylistCommand(IUserService userService, IPlaylistService playlistService, ISongService songService){
        this.userService = userService;
        this.playlistService = playlistService;
        this.songService = songService;
    }

    @Override
    public void execute(List<String> tokens) {
        try{
            int numOfSongs = tokens.size() - 3;
            String playlistName = String.valueOf(tokens.get(2));
            String userId = String.valueOf(tokens.get(1));
            Playlist playlist = playlistService.createPlaylist(userId, playlistName, tokens,  numOfSongs);
            System.out.print("Playlist ID - " + playlist.getId()+"\n");
        }catch(UserNotFoundException e){
            e.getMessage();
        }catch(SongNotFoundException e){
            e.getMessage();
        }

        
    }

}
