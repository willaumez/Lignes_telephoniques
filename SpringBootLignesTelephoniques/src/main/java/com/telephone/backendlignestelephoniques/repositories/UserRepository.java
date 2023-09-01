package com.telephone.backendlignestelephoniques.repositories;

import com.telephone.backendlignestelephoniques.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    @Query("SELECT c FROM User c WHERE c.username LIKE %:kw% OR c.email LIKE %:kw%")
    List<User> searchUser(@Param("kw") String keyword);

    User findByUsernameOrEmail(String username, String email);

    //Page<User> findByUsernameContainsOrEmailContainsOrRoleContains(String kw, Pageable pageable);
    //Page<User> findByUsernameContainsOrEmailContainsOrRoleContains(String kw1, String kw2, String kw3, Pageable pageable);

    /*
        @Query("SELECT u FROM User u WHERE " +
                "(?1 IS NULL OR LOWER(u.username) LIKE LOWER(CONCAT('%', ?1, '%'))) AND " +
                "(?2 IS NULL OR LOWER(u.email) LIKE LOWER(CONCAT('%', ?2, '%'))) AND " +
                "(?3 IS NULL OR LOWER(u.role) LIKE LOWER(CONCAT('%', ?3, '%')))")
        Page<User> findByUsernameContainsOrEmailContainsOrRoleContains(String kw1, String kw2, String kw3, Pageable pageable);
    */

    /*@Query("SELECT u FROM User u WHERE " +
            "(LOWER(u.username) LIKE LOWER(CONCAT('%', :kw, '%'))) OR " +
            "(LOWER(u.email) LIKE LOWER(CONCAT('%', :kw, '%'))) OR " +
            "(LOWER(u.role) LIKE LOWER(CONCAT('%', :kw, '%')))")
    Page<User> getAllUsers(@Param("kw") String keyword, Pageable pageable);*/

    /*@Query("SELECT u FROM User u WHERE " +
            "(:kw IS NULL OR LOWER(u.username) LIKE LOWER(CONCAT('%', :kw, '%'))) AND " +
            "(:kw IS NULL OR LOWER(u.email) LIKE LOWER(CONCAT('%', :kw, '%'))) AND " +
            "(:kw IS NULL OR LOWER(u.role) LIKE LOWER(CONCAT('%', :kw, '%'))) AND " +
            "(:kw IS NULL OR STR(u.createdDate) LIKE CONCAT('%', :kw, '%'))")
    Page<User> getAllUsers(@Param("kw") String keyword, Pageable pageable);*/

    @Query("SELECT u FROM User u WHERE " +
            "(LOWER(u.username) LIKE LOWER(CONCAT('%', :kw, '%'))) OR " +
            "(LOWER(u.email) LIKE LOWER(CONCAT('%', :kw, '%'))) OR " +
            "(LOWER(u.role) LIKE LOWER(CONCAT('%', :kw, '%'))) OR " +
            "(:kw IS NULL OR STR(u.createdDate) LIKE CONCAT('%', :kw, '%'))")
    Page<User> getAllUsers(@Param("kw") String keyword, Pageable pageable);






}




