/**
 * This class generate a userDetails service from a userObject
 * It is used in application for authorization
 */
package info.patriceallary.chatop.configuration;

import info.patriceallary.chatop.domain.model.Role;
import info.patriceallary.chatop.domain.model.User;
import info.patriceallary.chatop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (userService.getUserByEmail(username).isPresent()) {
            User user = userService.getUserByEmail(username).get();
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    getGrantedAuthorities(user.getRoles())
            );
        } else {
            throw new UsernameNotFoundException("User Not Found");
        }
    }

    // assign application roles to user from his role in database
    private List<GrantedAuthority> getGrantedAuthorities(List<Role> roles) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getName()));
        }
        return authorities;
    }


}
