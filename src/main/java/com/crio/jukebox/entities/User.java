package com.crio.jukebox.entities;

import java.util.ArrayList;
import java.util.List;

public class User extends BaseEntity {
    private String userName;
    private List<String> playlists;
    private Playlist currentPlayList;
    private Song currentSong;
    private Song previousSong;
    private Song nextSong;

    public String getUserName(){
        return userName;
    }

    public List<String> getPlalists(){
        return playlists;
    }

    public Playlist getCurrentPlaylist(){
        return currentPlayList;
    }

    public Song currentSong(){
        return currentSong;
    }

    public Song previousSong(){
        return previousSong;
    }

    public Song nextSong(){
        return nextSong;
    }

    public void setCurrentSong(Song currentSong){
        this.currentSong = currentSong;
    }

    public void setPreviousSong(Song previousSong){
        this.previousSong = previousSong;
    }
    
    public void setNextSong(Song nextSong){
        this.nextSong = nextSong;
    }


    public User(String userName){
        this.userName = userName;
        playlists = new ArrayList<>();
        currentPlayList = null;
        currentSong = null;
        previousSong = null;
        nextSong = null;
        
    }

    public User (String id, String userName){
        this(userName);
        this.id = id;
    }

    public void addPlaylist(String playlistId){
        playlists.add(playlistId);
    }

    public void removePlaylist(String playlistId){
        int mark = 0;
        for(int i= 0; i < playlists.size(); i++){
            if(playlists.get(i).equals(playlistId)){
                mark = i;
                break;
            } 
        }
        playlists.remove(mark);
    }

    public boolean checkPlaylist(String playlistId){
        for(String id : playlists){
            if(id.equals(playlistId)) return true;
        }
        return false;
    }

    public void setCurrentPlaylist(Playlist currentPlayList){
        this.currentPlayList = currentPlayList;
    }

    @Override
    public String toString(){
        return id + " " + userName;
    }

}
