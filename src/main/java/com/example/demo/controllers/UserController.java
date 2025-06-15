package com.example.demo.controllers;

import com.example.demo.exceptions.BootcampException;
import com.example.demo.model.TokenDTO;
import com.example.demo.model.User;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class UserController {



   private final UserService userService;
    private  JwtEncoder jwtEncoder;

    @Autowired
    public UserController(UserService userService , JwtEncoder jwtEncoder) {
        this.userService = userService;
        this.jwtEncoder = jwtEncoder;
    }



   @GetMapping("/users")
    public ArrayList<User> getUser(){
       return userService.getAllUsers();

    }


    @GetMapping(value = "/users", params = "id")
    public User getUserByParamId(@RequestParam Long id)throws BootcampException{

       return userService.getUserById(id);

    }

    @GetMapping(value = "/users/{id}")
    public User getUserByPathId(@PathVariable long id) throws BootcampException {
       return userService.getUserById(id);
    }


@PostMapping(value = "/users/Signup")
    public User createUser(@RequestBody User user) throws BootcampException{
        //return userService.addUser(user);
    return userService.addUser(user);


}

@PostMapping(value = "/users/login")
public TokenDTO login(Authentication authentication) throws BootcampException {

    User loggedInUser = ((User)authentication.getPrincipal());

    Instant now = Instant.now();
    JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuer("self")
            .issuedAt(now)
            .expiresAt(now.plus(6, ChronoUnit.HOURS))
            .subject(loggedInUser.getUsername())
            .claim("id", loggedInUser.getId())
            .claim("username", loggedInUser.getUsername())
            .build();


    String token = jwtEncoder.encode(JwtEncoderParameters.from(claims))
            .getTokenValue();

    TokenDTO tokenDTO = new TokenDTO();
    tokenDTO.setToken(token);
    return tokenDTO;
}


@PutMapping(value ="/users",params = "id")
    public User userToUpdate(@RequestParam Long id,@RequestBody User user)throws BootcampException{

    userService.updateUser(id,user);
    return null;
}

@DeleteMapping(value="users",params="id")
    public User deleteUser(@RequestParam Long id)throws BootcampException{

    return userService.deleteUserById(id);
}






    @DeleteMapping(value = "/users/{id}")
    public User deletePathUser(@PathVariable Long id)throws BootcampException{

        return userService.deleteUserById(id);
    }




}
