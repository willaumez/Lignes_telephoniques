package com.telephone.backendlignestelephoniques.services.User;

import com.telephone.backendlignestelephoniques.entities.User;
import com.telephone.backendlignestelephoniques.exceptions.UserNotFoundException;
import com.telephone.backendlignestelephoniques.repositories.UserRepository;
import com.telephone.backendlignestelephoniques.services.Historique.HistoriqueService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public User saveUser(User user, String operateur) {
        String password = user.getPassword();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        user.setPassword(hashedPassword);

        user.setCreatedDate(new Date());
        User savedUser = userRepository.save(user);
        historiqueService.saveHistoriques("Ajout [User]", savedUser.getUsername(), operateur);
        return savedUser;
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
        if (user.getPassword() != null) {
            String password = user.getPassword();
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(password);
            user.setPassword(hashedPassword);
        }
        User existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        user.setCreatedDate(existingUser.getCreatedDate());
        User updatedUser = userRepository.save(user);
        historiqueService.saveHistoriques("Mise à jour [TypeLigne]", updatedUser.getUsername(), operateur);
        return updatedUser;
    }

    @Override
    public List<User> listUsers() {
        return userRepository.findAll();
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

    /*@Override
    public UserDetails getLoggedInUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return (UserDetails) authentication.getPrincipal();
        }
        return null; // Aucun utilisateur connecté ou informations non disponibles
    }*/


}
