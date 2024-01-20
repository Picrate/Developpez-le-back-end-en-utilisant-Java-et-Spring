/**
 * Used for managing Users BO in application
 */
package info.patriceallary.chatop.services;

import info.patriceallary.chatop.domain.model.User;
import info.patriceallary.chatop.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public boolean userExists(String email) {
        return getUserByEmail(email).isPresent();
    }

    public void saveUser(User user) {userRepository.save(user);
    }

}
