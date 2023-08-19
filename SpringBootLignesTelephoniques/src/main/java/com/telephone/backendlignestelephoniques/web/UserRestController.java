package com.telephone.backendlignestelephoniques.web;


import com.telephone.backendlignestelephoniques.entities.User;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;
import com.telephone.backendlignestelephoniques.exceptions.UserNotFoundException;
import com.telephone.backendlignestelephoniques.services.User.UserServices;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class UserRestController {
    private UserServices userServices;

    @GetMapping("/users")
    public List<User> getUsers() {
        return userServices.listUsers();
    }

    @GetMapping("/users/search")
    public List<User> searchUsers(@RequestParam(name = "keyword", defaultValue = "") String keyword) {
        return userServices.searchUser("%"+keyword+"%");
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable(name = "id") Long userId) throws UserNotFoundException {
        return userServices.getUser(userId);
    }

    @PostMapping("/users/save/{operateur}")
    public User saveUser(@PathVariable String operateur, @RequestBody User user) {
        return userServices.saveUser(user, operateur);
    }

    @PutMapping("/users/update/{userId}/{operateur}")
    public User updateUser(@PathVariable Long userId, @PathVariable String operateur, @RequestBody User user) {
        user.setId(userId);
        return userServices.updateUser(user, operateur);
    }

    @DeleteMapping("/users/delete/{id}/{operateur}")
    public void deleteUser(@PathVariable Long id, @PathVariable String operateur) throws UserNotFoundException {
        userServices.deleteUser(id, operateur);
    }

    @GetMapping("/confirm/{id}/{password}")
    public boolean confirmPassword(@PathVariable Long id, @PathVariable String password)  throws UserNotFoundException{
        System.out.println( "Long id, @PathVariable String password--"+id+"   "+password);
        return userServices.confirmPassword(id,password);
    }


}
