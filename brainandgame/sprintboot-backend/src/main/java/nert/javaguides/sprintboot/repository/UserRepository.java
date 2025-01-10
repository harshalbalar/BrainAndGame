package nert.javaguides.sprintboot.repository;

import nert.javaguides.sprintboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Find a user by username
    Optional<User> findByUsername(String username);

    // Optional: Find users by role
    Optional<User> findByRole(String role);

    // Optional: Check if a username exists
    boolean existsByUsername(String username);
}
