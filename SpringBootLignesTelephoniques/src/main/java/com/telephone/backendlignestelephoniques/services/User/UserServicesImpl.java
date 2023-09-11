package com.telephone.backendlignestelephoniques.services.User;

import com.telephone.backendlignestelephoniques.entities.User;
import com.telephone.backendlignestelephoniques.exceptions.UserNotFoundException;
import com.telephone.backendlignestelephoniques.repositories.UserRepository;
import com.telephone.backendlignestelephoniques.services.Historique.HistoriqueService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class UserServicesImpl implements UserServices {

    private final UserRepository userRepository;
    private final HistoriqueService historiqueService;

    @Override
    public void saveUser(User user, String operateur) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Un Utilisateur du même nom existe déjà.");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Un Utilisateur avec le même Email existe déjà.");
        }
        String password = user.getPassword();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        user.setPassword(hashedPassword);

        user.setCreatedDate(new Date());
        User savedUser = userRepository.save(user);
        historiqueService.saveHistoriques("Ajout [User]", savedUser.getUsername(), operateur);
        //return savedUser;
    }

    @Override
    public User getUser(Long userId) throws UserNotFoundException {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("----- User Not found -----"));
    }

    @Override
    public void deleteUser(Long id, String operateur) throws UserNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
        userRepository.deleteById(id);
        historiqueService.saveHistoriques("Suppression [User]", user.getUsername(), operateur);
    }

    @Override
    public User updateUser(User user, String operateur) {
        if (user.getId() == null) {
            throw new IllegalArgumentException("L'ID de l'utilisateur ne doit pas être null");
        }
        User existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setCreatedDate(existingUser.getCreatedDate());

        if (user.getPassword() != null) {
            String password = user.getPassword();
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(password);
            user.setPassword(hashedPassword);
        }else {
            user.setPassword(existingUser.getPassword());
        }
        User updatedUser = userRepository.save(user);
        historiqueService.saveHistoriques("Mise à jour [TypeLigne]", updatedUser.getUsername(), operateur);
        return updatedUser;
    }

    /*@Override
    public List<User> listUsers() {
        return userRepository.findAll();
    }*/
    @Override
    public Page<User> listUsers(int page, int size, String kw) {
        Pageable pageable = PageRequest.of(page, size);
        //return userRepository.findByUsernameContainsOrEmailContainsOrRoleContains(kw, kw, kw, pageable);
        return userRepository.getAllUsers(kw, pageable);
    }

    @Override
    public List<User> searchUser(String keyword) {
        return userRepository.searchUser(keyword);
    }


    @Override
    public boolean confirmPassword(Long id, String password) throws UserNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User Not found"));

        String passwordUser = user.getPassword();

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(password, passwordUser);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsernameOrEmail(username, username);
    }
    //==========================================================================================================//


}
