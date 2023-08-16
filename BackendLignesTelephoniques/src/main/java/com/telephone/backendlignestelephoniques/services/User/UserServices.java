package com.telephone.backendlignestelephoniques.services.User;

import com.telephone.backendlignestelephoniques.entities.User;
import com.telephone.backendlignestelephoniques.exceptions.UserNotFoundException;

import java.util.List;

public interface UserServices {
    User saveUser(User user);
    User getUser(Long userId) throws UserNotFoundException;
    void deleteUser(Long id);
    User updateUser(User user);
    List<User> listUsers();
    List<User> searchUser(String keyword);

    boolean confirmPassword(Long id, String password) throws UserNotFoundException ;

    User getUserByUsername(String username);


    //=====================================================================================================//


}
