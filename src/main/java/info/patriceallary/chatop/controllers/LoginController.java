package info.patriceallary.chatop.controllers;

import info.patriceallary.chatop.domain.dto.LoginDto;
import info.patriceallary.chatop.domain.dto.RegisterDto;
import info.patriceallary.chatop.domain.dto.TokenDto;
import info.patriceallary.chatop.domain.dto.UserDto;
import info.patriceallary.chatop.domain.model.User;
import info.patriceallary.chatop.services.DtoService;
import info.patriceallary.chatop.services.JWTService;
import info.patriceallary.chatop.services.RoleService;
import info.patriceallary.chatop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DtoService dtoService;

    private final JWTService jwtService;

    private LoginController(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    /**
     * Login existing user with email and password
     *
     * @param loginDto data send through request
     * @return OK Response (200) with Bearer Token in body if login succeeded or unauthorized(401) Response
     */
    @PostMapping("/login")
    public ResponseEntity<TokenDto> authenticateUser(@RequestBody LoginDto loginDto) {

        ResponseEntity<TokenDto> response = ResponseEntity.noContent().build();
        // Authenticate user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getLogin(),
                        loginDto.getPassword()
                )
        );

        if (authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // generate Bearer Token & convert to TokenDto
            // return OK response with Bearer Token in body
            String token = jwtService.generateToken(authentication);
            TokenDto tokenDto = new TokenDto();
            tokenDto.setToken(token);
            response = ResponseEntity.ok(tokenDto);
        }
        return response;
    }

    /**
     * Register new user
     *
     * @param registerDto new account information
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<TokenDto> registerUser(@RequestBody RegisterDto registerDto) {
        // Default Response BadRequest(400)
        ResponseEntity<TokenDto> response = ResponseEntity.badRequest().build();
        // If Account information are not empty or null
        if (!registerDto.getEmail().isBlank()
                && !registerDto.getName().isBlank()
                && !registerDto.getPassword().isBlank()
        ) {
            // If User not exists
            if (!userService.getUserByEmail(registerDto.getEmail()).isPresent()) {

                // Encode password
                String encodedPassword = passwordEncoder.encode(registerDto.getPassword());
                // Create new User
                User newUser = dtoService.convertToUserEntity(registerDto);
                newUser.setPassword(encodedPassword);
                // Assign Role "USER" to new user
                newUser.addRole(roleService.findByName("USER").get());
                // Save new User in database
                userService.saveUser(newUser);
                // Authenticate new user for retrieving bearerToken
                Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(
                        registerDto.getEmail(),
                        registerDto.getPassword()
                );
                Authentication authenticationResponse = authenticationManager.authenticate(authenticationRequest);
                String token = jwtService.generateToken(authenticationResponse);
                TokenDto tokenDto = new TokenDto();
                tokenDto.setToken(token);

                // Send OK Response with BearerToken in body
                response = ResponseEntity.ok(tokenDto);

            }
        }
        return response;
    }

    /**
     * Send logged-in user account information
     *
     * @param principal BearerTokon of logged in user
     * @return Account information without password
     */
    @GetMapping("/me")
    public ResponseEntity<UserDto> aboutMe(JwtAuthenticationToken principal) {
        UserDto dto;
        // if user exists
        if (userService.getUserByEmail(principal.getName()).isPresent()) {
            // Retrieve user datas & Translate to Dto
            dto = dtoService.convertToUserDto(
                    userService.getUserByEmail(
                            principal.getName()
                    ).get()
            );
        } else {
            return ResponseEntity.notFound().build();
        }
        // Return account information
        return ResponseEntity.ok(dto);
    }
}
