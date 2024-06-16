package com.crio.jukebox.entities;

import java.util.List;

public class Song extends BaseEntity{
    private String songName;
    private String genre;
    private String albumName;
    private String ownerArtist;
    private List<String> featuredArtists;

    public String getSongName(){
        return songName;
    }

    public String getGenre() {
        return genre;
    }

    public String getAlbumName(){
        return albumName;
    }

    public String getOwnerArtist(){
        return ownerArtist;
    }

    public List<String> getFeaturedArtist(){
        return featuredArtists;
    }

    public Song(String id,String songName, String genre, String albumName, String ownerArtist, List<String> featuredArtist){
        this(songName, genre, albumName, ownerArtist, featuredArtist);
        this.id = id;
    }

    public Song(String songName, String genre, String albumName, String ownerArtist, List<String> featuredArtist){
        this.songName = songName;
        this.genre = genre;
        this.albumName = albumName;
        this.ownerArtist = ownerArtist;
        this.featuredArtists = featuredArtist;
    }

    @Override
    public String toString(){
        return "Song[" +
                "songId= " + id + '\'' +
                ", songName='" + songName + '\'' +
                ", genre='" + genre + '\'' +
                ", albumName='" + albumName + '\'' +
                ", ownerArtist='" + ownerArtist + '\'' +
                ", featuredArtists=" + featuredArtists +
                ']';
    }


}
