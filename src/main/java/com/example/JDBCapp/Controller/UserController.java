package com.example.JDBCapp.Controller;

import com.example.JDBCapp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.JDBCapp.bean.User;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/test")
    public String test()
    { return "testing"; }

    @GetMapping("/dataSet")
    public List<User> getAllUsers()
    { return userRepository.getUser(); }

    @GetMapping("/dataSet/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") Integer id)
    {
        User user = userRepository.findById(id);

        if(user == null)
        { return new ResponseEntity<String>("No user Found with the ID " + id, HttpStatus.NOT_FOUND); }

        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user) throws SQLIntegrityConstraintViolationException {

        if(userRepository.findById(user.getID()) != null)
        { return new ResponseEntity<String>("Duplicate Entry ID." + user.getID(), HttpStatus.IM_USED); }

        userRepository.saveUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping(value="dataSet/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Integer id)
    {
        User user = userRepository.findById(id);

        if(user == null)
        { return new ResponseEntity<String>("User ID." + id + " Not found", HttpStatus.NOT_FOUND); }

        userRepository.deleteUserById(id);

        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }


    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user)
    {
        if(userRepository.findById(user.getID()) == null)
        { return new ResponseEntity<String>( "User ID." + user.getID() + " Not Found", HttpStatus.NOT_FOUND); }

        userRepository.updateUser(user);

        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

}
