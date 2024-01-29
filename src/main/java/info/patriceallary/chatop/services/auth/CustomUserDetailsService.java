/**
 * This class generate a userDetails service from a userObject
 * It is used in application for authorization
 */
package info.patriceallary.chatop.services.auth;

import info.patriceallary.chatop.domain.model.Role;
import info.patriceallary.chatop.domain.model.User;
import info.patriceallary.chatop.services.domain.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userService.getUserByEmail(username);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User Not Found");
        }
        User user = optionalUser.get();
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                getGrantedAuthorities(user.getRoles())
        );

    }

    // assign application roles to user from his role in database
    private List<GrantedAuthority> getGrantedAuthorities(List<Role> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        }
        return authorities;
    }


}
