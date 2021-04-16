package com.opsec.services;

import com.opsec.exceptions.ResourceNotFoundException;
import com.opsec.model.DatabaseSequence;
import com.opsec.model.User;
import com.opsec.repository.UserRepository;
import com.opsec.utils.SequenceGeneratorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;

    @Autowired
    SequenceGeneratorUtil sequenceGeneratorUtil;

    @Override
    public User createUser(User user) {
        user.setId(sequenceGeneratorUtil.generateSequence(User.SEQUENCE_NAME));
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(long id) throws ResourceNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("" +
                        "User not found for this id :: " + id));
    }

    @Override
    public User updateUser(long id, User user) throws ResourceNotFoundException{
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + id));

        existingUser.setFirstName(user.getFirstName());
        existingUser.setSurName(user.getSurName());
        existingUser.setDob(user.getDob());
        existingUser.setTitle(user.getTitle());
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(long id) throws ResourceNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + id));
        userRepository.delete(user);
    }
}
