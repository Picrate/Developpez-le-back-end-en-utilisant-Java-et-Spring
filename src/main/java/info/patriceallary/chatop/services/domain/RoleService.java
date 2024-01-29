/**
 * Used for managing Roles BO in application
 */
package info.patriceallary.chatop.services;

import info.patriceallary.chatop.domain.model.Role;
import info.patriceallary.chatop.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Optional<Role> findByName(String name) {
        return this.roleRepository.findByName(name);
    }


}
