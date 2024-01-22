package info.patriceallary.chatop.services;

import info.patriceallary.chatop.domain.dto.LoginDto;
import info.patriceallary.chatop.domain.dto.RegisterDto;
import info.patriceallary.chatop.domain.model.User;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LoginAndRegisterService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    private final RoleService roleService;
    private final DtoService dtoService;
    private final PasswordEncoder passwordEncoder;

    public LoginAndRegisterService(AuthenticationManager authenticationManager, DtoService dtoService, UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.roleService = roleService;
        this.dtoService = dtoService;
        this.passwordEncoder = passwordEncoder;
    }

    public Authentication authenticateUser(LoginDto loginDto) {

        log.info(loginDto.getLogin()+ " "+loginDto.getPassword());

        // Prepared authentication as unauthenticated request
        Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(
                loginDto.getLogin(),
                loginDto.getPassword()
        );
        // Perform authentication
        return this.authenticationManager.authenticate(authenticationRequest);
    }

    public Authentication registerNewUser(RegisterDto registerDto) {

        User newUser = dtoService.convertToUserEntity(registerDto);
        // Assign Role "USER" to new user
        if (this.roleService.findByName("USER").isPresent()) {
            newUser.addRole(this.roleService.findByName("USER").get());
        }
        newUser.setPassword(this.passwordEncoder.encode(registerDto.getPassword()));
        // Save new User in database
        this.userService.saveUser(newUser);
        // Authenticate User
        return this.authenticateUser(dtoService.convertRegisterDtoToLoginDto(registerDto));
    }


}