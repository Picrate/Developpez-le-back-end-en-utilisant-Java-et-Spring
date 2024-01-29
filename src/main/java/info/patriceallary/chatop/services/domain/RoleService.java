/**
 * Used for managing Roles BO in application
 */
package info.patriceallary.chatop.services.domain;

import info.patriceallary.chatop.domain.model.Role;
import info.patriceallary.chatop.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {


    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Optional<Role> findByName(String name) {
        return this.roleRepository.findByName(name);
    }


}
