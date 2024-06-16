package com.crio.jukebox.services;

import java.io.IOException;
import java.util.List;
import com.crio.jukebox.entities.Song;

public interface ISongService {
    
    void loadData(String csvFile) throws IOException;
    boolean checkIfSongsExist(List<String> songIds);
    public boolean findById(String id);
    public Song findSongById(String id);

}
