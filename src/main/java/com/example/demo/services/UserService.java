package com.example.demo.services;

import com.example.demo.exceptions.BootcampException;
import com.example.demo.model.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {


   private  UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {

        this.userRepository=userRepository;
    }



    public ArrayList<User> getAllUsers() {

        return (ArrayList<User>) userRepository.findAll();
    }


    public User getUserById(Long id) throws BootcampException {


        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            return user.get();
        }

        throw new BootcampException(HttpStatus.NOT_FOUND,"User not found");
    }

    public User getUserByName(String name) throws BootcampException {
        Optional<User> user =userRepository.findByEmail(name);
        if(user.isPresent()) {
            return user.get();
        }
        throw new BootcampException(HttpStatus.NOT_FOUND,"User not found");
    }



    public User addUser(User user)throws BootcampException {

        for(User user1: getAllUsers()) {
            if(user1.getEmail().equals(user.getEmail())){
               throw new BootcampException(HttpStatus.CONFLICT,"User already exists");
           }

       }
      return   userRepository.save(user);



    }


    public User updateUser(Long id,User user) throws BootcampException {

        User userToUpdate=getUserById(id);
        userToUpdate.setName(user.getName());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setPassword(user.getPassword());
        userRepository.save(userToUpdate);
         return userToUpdate;
    }

    public User deleteUserById(Long id) throws BootcampException {

        User userToDelete = getUserById(id);
        userRepository.delete(userToDelete);
        return userToDelete;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user=null;

        try {
            user=this.getUserByName(username);
            //user=userRepository.findByEmail(username);
            return user;

        } catch (BootcampException e) {
            throw new UsernameNotFoundException("User not found "+ username ,e);
        }


    }
}
