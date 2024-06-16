package com.crio.jukebox.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.repositories.ISongRepository;

public class SongService implements ISongService {
    private final ISongRepository songRepository;
    
    public SongService(ISongRepository songRepository){
        this.songRepository = songRepository;
    }

    @Override
    public void loadData(String csvFile) throws IOException{

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Song song = parseSong(line);
                Song sveSong = songRepository.save(song);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Song parseSong(String line) {
        String[] entry = line.split(",");
        String songName = entry[0];
        String genral = entry[1];
        String album_Name = entry[2];
        String Artist = entry[3];
        List<String> featured_Artists = Arrays.asList(entry[4].split("#"));

        return new Song(songName, genral, album_Name, Artist, featured_Artists);
    }

    @Override
    public boolean checkIfSongsExist(List<String> songIds) {
        for(String i : songIds){
            if(!songRepository.existsById(i)) return false;
        }
        return true;
    }
    @Override 
    public boolean findById(String id){
        if(songRepository.existsById(id)) return true;
        return false;
    }

    @Override 
    public Song findSongById(String id){
        return songRepository.findSong(id);

    }

    

}
