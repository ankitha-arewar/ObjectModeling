package com.crio.jukebox.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.crio.jukebox.entities.User;

public class UserRepository implements IUserRepository{

    private final Map<String,User> userMap;
    private Integer autoIncrement = 0; 

    public UserRepository(){
        userMap = new HashMap<>();
    }

    public UserRepository(Map<String, User> userMap, Integer autoIncrement){
        this.userMap = userMap;
        this.autoIncrement = autoIncrement;
    }

    @Override
    public User save(User entity) {
        if( entity.getId() == null ){
            autoIncrement++;
            User u = new User(Integer.toString(autoIncrement),entity.getUserName());
            userMap.put(u.getId(),u);
            return u;
        }
        userMap.put(entity.getId(),entity);
        return entity;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        for (Map.Entry<String,User> mapElement : userMap.entrySet()) {
            users.add(mapElement.getValue());
        }
        return users;
    }

    @Override
    public Optional<User> findById(String id) {
        for (Map.Entry<String,User> mapElement : userMap.entrySet()) {
            String userId = mapElement.getValue().getId();
            if(userId.equals(id)) return Optional.of(mapElement.getValue());
            
        }
        return Optional.empty();
    }

    // public User getById(String id){

    // }

    @Override
    public boolean existsById(String id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void delete(User entity) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteById(String id) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public long count() {
        // TODO Auto-generated method stub
        return 0;
    }
    
}
