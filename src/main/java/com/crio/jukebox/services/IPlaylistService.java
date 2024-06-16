package com.crio.jukebox.services;

import java.util.List;
import com.crio.jukebox.exceptions.UserNotFoundException;
import com.crio.jukebox.entities.Playlist;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.entities.User;
import com.crio.jukebox.exceptions.SongNotFoundException;

public interface IPlaylistService{
    public Playlist createPlaylist(String userId, String playlistName, List<String> songIds,  int noOfSongs) throws UserNotFoundException, SongNotFoundException;
    public void deletePlaylist(String userId, String playlistId);
    public Playlist addSongInPlaylist(String userId, String playlistId, List<String> tokens);
    public Playlist deleteSongInPlaylist(String userId, String playlistId, List<String> tokens);
    public Song playSong(String userId, String playListId);
    public User nextSong(String userId);
    public User previousSong(String userId);
    public User playSongFromId(String userId, String songId);
}

