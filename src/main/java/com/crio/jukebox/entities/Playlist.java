package com.crio.jukebox.entities;

import java.util.List;

public class Playlist extends BaseEntity{
    private String userId;
    private String playlistName;
    private List<String> songIds;


    public Playlist(String userId, String playlistName, List<String> songIds) {
        this.userId = userId;
        this.playlistName = playlistName;
        this.songIds = songIds;
    }


    public Playlist(String id, String playlistName, String userId, List<String> songIds) {
        this(userId, playlistName, songIds);
        this.id = id;
    }


    public String getUserId(){
        return userId;
    }

    public String getPlaylistName(){
        return playlistName;
    }

    public List<String> getSongIds(){
        return songIds;
    }

    public boolean findSongId(String songId){
        for(String id : songIds){
            if(id.equals(songId)) return true;
        }
        return false;
    }

    public void addSongId(String songId){
        songIds.add(songId);
    }

    public void removeSongIds(List<String> songIdsToRemove){
        for(String songId : songIdsToRemove){
            songIds.remove(songId);
        }
    }

    @Override
    public String toString(){
        return "userId " + userId + "playlistId " + id; 
    }
}


