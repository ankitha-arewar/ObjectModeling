package com.crio.jukebox.services;

import com.crio.jukebox.repositories.IUserRepository;
import java.util.Optional;
import com.crio.jukebox.entities.Playlist;
import com.crio.jukebox.entities.User;

public class UserService implements IUserService{

    private final IUserRepository userRepository;
    
    public UserService(IUserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public Boolean findById(String id){
        if(!userRepository.existsById(id)) return true;;
        return false;
    }

    @Override
    public User create(String name) {
        User userName = new User(name);
        return userRepository.save(userName);
    }

    @Override
    public User savePlaylist(Playlist savedPlaylist, String userId) {
        User userID = userRepository.findById(userId).get();
        userID.addPlaylist(savedPlaylist.getId());
        User savedUser = userRepository.save(userID);
        return savedUser;
    }

    @Override
    public User findEntityById(String id){
        Optional<User> userID = userRepository.findById(id);
        if(userID.isEmpty()) return null;
        return userID.get();
    }

    @Override
    public void removePlaylist(String userId, String playlistId) {
        User userID = userRepository.findById(userId).get();
        userID.removePlaylist(playlistId);
        userRepository.save(userID);
    }

    @Override
    public User updateUser(User user) {
        User savedUser = userRepository.save(user);
        return savedUser;
    }
    

    
}
