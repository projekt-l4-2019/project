package pl.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;
import pl.example.models.UserEntity;
import pl.example.service.UserService;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/users")
    @CrossOrigin(origins = "*")
    public List<UserEntity> getAllUser() {
        return userService.getAllUser();
    }

    @RequestMapping("/users/{id}")
    @CrossOrigin(origins = "*")
    public UserEntity getNotice(@PathVariable Integer id) {
        return userService.getUser(id);
    }

    @RequestMapping(method = RequestMethod.POST, value="/users")
    @CrossOrigin(origins = "*")
    public void addNotice(@RequestBody UserEntity user) {
        userService.addUser(user);
    }

    @RequestMapping(method = RequestMethod.PUT, value="/users/{id}")
    @CrossOrigin(origins = "*")
    public void updateNotice(@RequestBody UserEntity user, @PathVariable Integer id) {
        userService.updateUser(id, user);
    }

    @RequestMapping(method = RequestMethod.DELETE, value="/users/{id}")
    @CrossOrigin(origins = "*")
    public void deleteNotice(@PathVariable Integer id) {
        userService.deleteUser(id);
    }
}
