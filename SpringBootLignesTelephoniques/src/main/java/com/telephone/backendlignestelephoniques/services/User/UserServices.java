package com.telephone.backendlignestelephoniques.services.User;

import com.telephone.backendlignestelephoniques.entities.User;
import com.telephone.backendlignestelephoniques.exceptions.UserNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserServices {
    void saveUser(User user, String operateur);
    User getUser(Long userId) throws UserNotFoundException;
    void deleteUser(Long id, String operateur) throws UserNotFoundException;
    User updateUser(User user, String operateur);

    /*List<User> listUsers();*/

    List<User> searchUser(String keyword);

    boolean confirmPassword(Long id, String password) throws UserNotFoundException ;

    User getUserByUsername(String username);

    //pagination
    Page<User> listUsers(int page, int size, String kw);

    //UserDetails getLoggedInUserDetails();


    //=====================================================================================================//


}
