package com.crio.jukebox.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.crio.jukebox.entities.Playlist;

public class PlaylistRepository implements IPlaylistRepository {

    private final Map<String,Playlist> playlistMap;
    private Integer autoIncrement = 0;


    public PlaylistRepository(){
        playlistMap = new HashMap<>();
    }

    public PlaylistRepository(Map<String, Playlist> playlistMap, Integer autoIncrement){
        this.playlistMap = playlistMap;
        this.autoIncrement = autoIncrement;
    }

    @Override
    public Playlist save(Playlist entity) {
        if(entity.getId() == null){
            autoIncrement++;
            Playlist p = new Playlist(Integer.toString(autoIncrement),entity.getPlaylistName(), entity.getUserId(), entity.getSongIds());
            playlistMap.put(p.getId(),p);
            return p;
        }
        playlistMap.put(entity.getId(),entity);
        return entity;
    }

    @Override
    public List<Playlist> findAll() {
        List<Playlist> list = new ArrayList<>();
        for (Map.Entry<String,Playlist> mapElement : playlistMap.entrySet()) {
            list.add(mapElement.getValue());
        }
        return list;
    }

    @Override
    public Optional<Playlist> findById(String id) {
        if(playlistMap.containsKey(id)) return Optional.of(playlistMap.get(id));
        return null;
    }

    @Override
    public boolean existsById(String id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void delete(Playlist entity) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteById(String id) {
        playlistMap.remove(id);        
    }

    @Override
    public long count() {
        // TODO Auto-generated method stub
        return 0;
    }
    
}
