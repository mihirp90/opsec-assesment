package com.opsec.services;

import com.opsec.exceptions.ResourceNotFoundException;
import com.opsec.model.User;

import java.util.List;

public interface UserService {
    public User createUser(User user);
    public List<User> getAllUsers();
    public User getUserById(long id) throws ResourceNotFoundException;
    public User updateUser(long id, User user) throws ResourceNotFoundException;
    public void deleteUser(long id) throws ResourceNotFoundException;
}
