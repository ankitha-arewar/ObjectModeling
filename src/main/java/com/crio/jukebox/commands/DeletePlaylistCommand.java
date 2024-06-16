package com.crio.jukebox.commands;

import java.util.List;
import com.crio.jukebox.exceptions.PlaylistNotFoundException;
import com.crio.jukebox.exceptions.UserNotFoundException;
import com.crio.jukebox.services.IPlaylistService;
import com.crio.jukebox.services.IUserService;

public class DeletePlaylistCommand implements ICommand{

    private final IPlaylistService playlistService;
    private final IUserService userService;

    public DeletePlaylistCommand(IPlaylistService playlistService, IUserService userService){
        this.playlistService = playlistService;
        this.userService = userService;
    }


    @Override
    public void execute(List<String> tokens) {
        try{
            String userID = tokens.get(1);
            String playlistID = tokens.get(2);
            playlistService.deletePlaylist(userID, playlistID);
            System.out.print("Delete Successful"+"\n");
        }catch(UserNotFoundException e){
            e.getMessage();
        }
        catch(PlaylistNotFoundException e){
            e.getMessage();
        }
        
    }
    
}
