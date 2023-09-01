package com.telephone.backendlignestelephoniques.web;


import com.telephone.backendlignestelephoniques.entities.User;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;
import com.telephone.backendlignestelephoniques.exceptions.UserNotFoundException;
import com.telephone.backendlignestelephoniques.services.User.UserServices;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class UserRestController {
    private UserServices userServices;

   /* @GetMapping("/users")
    public List<User> getUsers() {
        return userServices.listUsers();
    }*/

    // Méthode avec numéro de page et taille de page spécifiés
    @GetMapping("/users")
    public ResponseEntity<Map<String, Object>> getUsers(@RequestParam(name = "page", defaultValue = "0") int page,
                                                        @RequestParam(name = "size", defaultValue = "10") int size,
                                                        @RequestParam(name = "kw", defaultValue = "") String kw) {
        Page<User> pageUsers = userServices.listUsers(page, size, kw);

        List<User> users = pageUsers.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("dataElements", users);
        response.put("currentPage", pageUsers.getNumber());
        response.put("totalItems", pageUsers.getTotalElements());
        response.put("totalPages", pageUsers.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
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
    public void saveUser(@PathVariable String operateur, @RequestBody User user) {
        userServices.saveUser(user, operateur);
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
