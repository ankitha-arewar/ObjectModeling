package com.crio.jukebox.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.crio.jukebox.exceptions.UserNotFoundException;
import com.crio.jukebox.entities.Playlist;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.entities.User;
import com.crio.jukebox.exceptions.PlaylistNotFoundException;
import com.crio.jukebox.exceptions.SongNotFoundException;
import com.crio.jukebox.repositories.IPlaylistRepository;

public class PlaylistService implements IPlaylistService{
    private final IPlaylistRepository playlistRepository;
    private final ISongService songService;
    private final IUserService userService;
    
    public PlaylistService(IPlaylistRepository playlistRepository, ISongService songService, IUserService userService){
        this.playlistRepository = playlistRepository;
        this.songService = songService;
        this.userService = userService;
    }

    public Playlist createPlaylist(String userId, String playlistName, List<String> tokens,  int noOfSongs) throws UserNotFoundException, SongNotFoundException{
        if(noOfSongs <= 0) throw new SongNotFoundException("Some Requested Songs Not Available. Please try again.");
        List<String> songId = new ArrayList<>();
            for(int i = 3; i < tokens.size(); i++){
                if(!songService.findById(String.valueOf(tokens.get(i))))
                throw new SongNotFoundException("Error Message if Any of the Above Song IDs is not present in the pool.");
                songId.add(String.valueOf(tokens.get(i)));
        }

        if(!userService.findById(userId)) throw new UserNotFoundException("User Not found Try again.");

        Playlist playlist = new Playlist(userId, playlistName, songId);
        Playlist savedPlaylist = playlistRepository.save(playlist);

        // add playlist correspoding to the user
        User updatedUser = userService.savePlaylist(savedPlaylist, userId);
        List<Playlist> list = playlistRepository.findAll();
        return savedPlaylist;

    }

    @Override
    public void deletePlaylist(String userId, String playlistId) {
        User userID = userService.findEntityById(userId);
        if(userID == null) throw new UserNotFoundException("User does not exits");

        Optional<Playlist> playlist = playlistRepository.findById(playlistId);
        if(playlist.isEmpty()) throw new PlaylistNotFoundException("Playlist does not exist");
        
        playlistRepository.deleteById(playlistId);
        userService.removePlaylist(userId, playlistId);
    }

    @Override
    public Playlist addSongInPlaylist(String userId, String playlistId, List<String> tokens) {
        //user not found exception
        User userID = userService.findEntityById(userId);
        if(userID == null) throw new UserNotFoundException("User does not exits");

        //number of songs are less than or equla to 0
        List<String> songIds = new ArrayList<>();
        int noOfSongs = tokens.size() - 4;
        if(noOfSongs <= 0)throw new SongNotFoundException("Some Requested Songs Not Available. Please try again.");
        if(playlistRepository.findById(playlistId).isEmpty())throw new PlaylistNotFoundException("Playlist does not exist");
        
        
        for(int i = 4; i < tokens.size(); i++){
            //check if song exists or not
            if(!songService.findById(String.valueOf(tokens.get(i))))
            throw new SongNotFoundException("Error Message if Any of the Above Song IDs is not present in the pool.");
            songIds.add(String.valueOf(tokens.get(i)));
        }
        Playlist playlist = playlistRepository.findById(playlistId).get();
        if(!userId.equals(playlist.getUserId())) throw new UserNotFoundException("Invalid User and playlist combination");
        for(String songId : songIds){
            if(playlist.findSongId(songId) == false)
                playlist.addSongId(songId);
        }
        Playlist savedPlaylist = playlistRepository.save(playlist);
        if(userID.getCurrentPlaylist() != null){
            if(userID.getCurrentPlaylist().getId().equals(savedPlaylist.getId())){
                userID.setCurrentPlaylist(savedPlaylist);
                userService.updateUser(userID);
            }
        }
        return savedPlaylist;
    }

    @Override
    public Playlist deleteSongInPlaylist(String userId, String playlistId, List<String> tokens) {
        User userID = userService.findEntityById(userId);
        if(userID == null) throw new UserNotFoundException("User does not exits");
        if(!userID.checkPlaylist(playlistId)) throw new PlaylistNotFoundException("PLaylist does not exist");


        //number of songs are less than or equla to 0
        int noOfSongs = tokens.size() - 4;
        if(noOfSongs <= 0)throw new SongNotFoundException("Some Requested Songs Not Available. Please try again.");
        if(playlistRepository.findById(playlistId).isEmpty())throw new PlaylistNotFoundException("Playlist does not exist");

        List<String> songIds = new ArrayList<>();
        for(int i = 4; i < tokens.size(); i++){
            //check if song exists or not
            if(!songService.findById(String.valueOf(tokens.get(i))))
            throw new SongNotFoundException("Error Message if Any of the Above Song IDs is not present in the pool.");
            songIds.add(String.valueOf(tokens.get(i)));
        }
        
        Playlist playlist = playlistRepository.findById(playlistId).get();


        playlist.removeSongIds(songIds);

        Playlist savedPlaylist = playlistRepository.save(playlist);
        if(userID.getCurrentPlaylist() != null){
            if(userID.getCurrentPlaylist().getId().equals(savedPlaylist.getId())){
                userID.setCurrentPlaylist(savedPlaylist);
                userService.updateUser(userID);
            }
        }

        return savedPlaylist;
    }

    @Override
    public Song playSong(String userId, String playListId) {
        User userID = userService.findEntityById(userId);
        if(userID == null) throw new UserNotFoundException("User does not exis");

        if( playlistRepository.findById(playListId).isEmpty()) throw new PlaylistNotFoundException("Playlist not found");
        Playlist playlist = playlistRepository.findById(playListId).get();

        if(!userID.checkPlaylist(playListId)) throw new PlaylistNotFoundException("Playlist not found");
        int sizeOfPlaylist = playlist.getSongIds().size();
        Song currentSong = songService.findSongById(playlist.getSongIds().get(0));
        Song previousSong = songService.findSongById(playlist.getSongIds().get(sizeOfPlaylist - 1));
        Song nextSong = songService.findSongById(playlist.getSongIds().get(1));
        
        userID.setCurrentPlaylist(playlist);
        userID.setCurrentSong(currentSong);
        userID.setNextSong(nextSong);
        userID.setPreviousSong(previousSong);

        User updatedUser = userService.updateUser(userID);

        return updatedUser.currentSong();
    }

    @Override
    public User nextSong(String userId) {
        User userID = userService.findEntityById(userId);
        if(userID == null) throw new UserNotFoundException("User does not exist");

        int newCurrentSongIndex = 0;
        int newNextSongIndex = 0;
        int newPreviousSongIndex = 0;
        Playlist playlist = userID.getCurrentPlaylist();
        Song currentSong = userID.currentSong();
        List<String> songIds = playlist.getSongIds();
        int index = 0;
        for(int i = 0; i< songIds.size(); i++){
            if(songIds.get(i).equals(currentSong.getId())){
                index = i;
                break;
            }
        }

        if(index == songIds.size() - 1){
            newPreviousSongIndex = index;
            newCurrentSongIndex = 0;
            newNextSongIndex = 1;
        }
        else if(index == songIds.size() - 2){
            newPreviousSongIndex = index;
            newCurrentSongIndex = songIds.size() - 1;
            newNextSongIndex = 0;
        }else{
            newPreviousSongIndex = index;
            newCurrentSongIndex = index + 1;
            newNextSongIndex = index + 2;
        }
        Song newCurrentSong = songService.findSongById(playlist.getSongIds().get(newCurrentSongIndex));
        Song newPreviousSong = songService.findSongById(playlist.getSongIds().get(newPreviousSongIndex));
        Song newNextSong = songService.findSongById(playlist.getSongIds().get(newNextSongIndex));
        userID.setCurrentSong(newCurrentSong);
        userID.setNextSong(newNextSong);
        userID.setPreviousSong(newPreviousSong);

        User savedUser = userService.updateUser(userID);
        return savedUser;
    }

    @Override
    public User previousSong(String userId) {
        User userID = userService.findEntityById(userId);
        if(userID == null) throw new UserNotFoundException("User does not exist");

        int newCurrentSongIndex = 0;
        int newNextSongIndex = 0;
        int newPreviousSongIndex = 0;
        Playlist playlist = userID.getCurrentPlaylist();
        Song currentSong = userID.currentSong();
        List<String> songIds = playlist.getSongIds();
        int index = 0;
        for(int i = 0; i< songIds.size(); i++){
            if(songIds.get(i).equals(currentSong.getId())){
                index = i;
                break;
            }
        }

        if(index == 0){
            newNextSongIndex = index;
            newCurrentSongIndex = songIds.size() - 1;
            newPreviousSongIndex = songIds.size() - 2;
        }else if(index == 1){
            newNextSongIndex = index;
            newCurrentSongIndex = 0;
            newPreviousSongIndex = songIds.size() - 1;
        }else if(index == songIds.size() - 1){
            newNextSongIndex = index ;
            newCurrentSongIndex = songIds.size() - 2;
            newPreviousSongIndex = songIds.size() - 3;
        }else{
            newNextSongIndex = index;
            newCurrentSongIndex = index - 1;
            newPreviousSongIndex = index - 2;
        }
        Song newCurrentSong = songService.findSongById(playlist.getSongIds().get(newCurrentSongIndex));
        Song newPreviousSong = songService.findSongById(playlist.getSongIds().get(newPreviousSongIndex));
        Song newNextSong = songService.findSongById(playlist.getSongIds().get(newNextSongIndex));
        userID.setCurrentSong(newCurrentSong);
        userID.setNextSong(newNextSong);
        userID.setPreviousSong(newPreviousSong);

        User savedUser = userService.updateUser(userID);
        return savedUser;
    }

    @Override
    public User playSongFromId(String userId, String songId) throws SongNotFoundException, UserNotFoundException
    {
        User userID = userService.findEntityById(userId);
        if(userID == null) throw new UserNotFoundException("User does not exist");

        Playlist playlist = userID.getCurrentPlaylist();
        List<String> songIds = playlist.getSongIds();
        int newCurrentSongIndex = -1;
        for(int i = 0; i < songIds.size(); i++){
            if(songIds.get(i).equals(songId)){
                newCurrentSongIndex = i;
                break;
            }
        }
        if(newCurrentSongIndex == -1){
            return null;
        } 
        int newNextSongIndex = -1;
        int newPreviousSongIndex = -1;
        if(newCurrentSongIndex == 0){
            newPreviousSongIndex = songIds.size() - 1;
            newNextSongIndex = 1;
        }else if(newCurrentSongIndex == songIds.size() -1){
            newNextSongIndex = 0;
            newPreviousSongIndex = songIds.size() - 2;
        }else{
            newNextSongIndex = newCurrentSongIndex + 1;
            newPreviousSongIndex = newCurrentSongIndex - 1;
        }

        Song newCurrentSong = songService.findSongById(playlist.getSongIds().get(newCurrentSongIndex));
        Song newPreviousSong = songService.findSongById(playlist.getSongIds().get(newPreviousSongIndex));
        Song newNextSong = songService.findSongById(playlist.getSongIds().get(newNextSongIndex));
        userID.setCurrentSong(newCurrentSong);
        userID.setNextSong(newNextSong);
        userID.setPreviousSong(newPreviousSong);

        User savedUser = userService.updateUser(userID);
        return savedUser;
    }

}
