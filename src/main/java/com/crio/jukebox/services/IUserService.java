package com.crio.jukebox.services;

import com.crio.jukebox.entities.Playlist;
import com.crio.jukebox.entities.User;

public interface IUserService {
    
    public Boolean findById(String id);
    public User create(String userName);
    public User savePlaylist(Playlist savedPlaylist, String userId);
    public User findEntityById(String id);
    public void removePlaylist(String userId, String playlistId);
    public User updateUser(User user);
}
