/**
 * Used for managing Users BO in application
 */
package info.patriceallary.chatop.services;

import info.patriceallary.chatop.domain.model.User;
import info.patriceallary.chatop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public void saveUser(User user) {userRepository.save(user);
    }

}
