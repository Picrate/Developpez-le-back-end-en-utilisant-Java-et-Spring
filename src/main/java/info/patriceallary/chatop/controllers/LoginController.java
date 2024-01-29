package info.patriceallary.chatop.controllers;

import info.patriceallary.chatop.domain.dto.LoginDto;
import info.patriceallary.chatop.domain.dto.RegisterDto;
import info.patriceallary.chatop.domain.dto.TokenDto;
import info.patriceallary.chatop.domain.dto.UserDto;
import info.patriceallary.chatop.domain.model.User;
import info.patriceallary.chatop.services.dto.DtoService;
import info.patriceallary.chatop.services.auth.JWTService;
import info.patriceallary.chatop.services.auth.LoginAndRegisterService;
import info.patriceallary.chatop.services.domain.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class LoginController {

    private final LoginAndRegisterService loginAndRegisterService;

    private final UserService userService;

    private final DtoService dtoService;

    private final JWTService jwtService;

    public LoginController(JWTService jwtService, LoginAndRegisterService loginAndRegisterService, DtoService dtoService, UserService userService) {
        this.jwtService = jwtService;
        this.loginAndRegisterService = loginAndRegisterService;
        this.dtoService = dtoService;
        this.userService = userService;
    }

    /**
     * Login existing user with email and password
     *
     * @param loginDto data send through request
     * @return OK Response (200) with Bearer Token in body if login succeeded or unauthorized(401) Response
     */
    @PostMapping("/login")
    public ResponseEntity<TokenDto> authenticateUser(@RequestBody @Valid LoginDto loginDto) {

        ResponseEntity<TokenDto> response = ResponseEntity.noContent().build();
        Authentication authentication = this.loginAndRegisterService.authenticateUser(loginDto);
        if(authentication.isAuthenticated()) {
            response = ResponseEntity.ok(
                    dtoService.convertToTokenDto(this.jwtService.generateToken(authentication))
            );
        }
        return response;
    }

    // Register new User
    @PostMapping("/register")
    public ResponseEntity<TokenDto> registerUser(@RequestBody @Valid RegisterDto registerDto) {
        // Default Response BadRequest(400)
        ResponseEntity<TokenDto> response = ResponseEntity.badRequest().build();

        Authentication authentication = this.loginAndRegisterService.registerNewUser(registerDto);
        if(authentication.isAuthenticated()) {
            response = ResponseEntity.ok(
                    dtoService.convertToTokenDto(this.jwtService.generateToken(authentication))
            );
        }
        return response;
    }

    /**
     * Send logged-in user account information
     *
     * @param principal BearerToken of logged in user
     * @return Account information without password
     */
    @GetMapping("/me")
    public ResponseEntity<UserDto> aboutMe(JwtAuthenticationToken principal) {
        UserDto dto;
        // if user exists
        if (userService.userExists(principal.getName())) {
            // Retrieve user datas & Translate to Dto
            Optional<User> optionalUser = userService.getUserByEmail(principal.getName());
            if(optionalUser.isEmpty()){
                throw new NoSuchElementException("User Not Found");
            }
            dto = dtoService.convertToUserDto(optionalUser.get());
        } else {
            return ResponseEntity.notFound().build();
        }
        // Return account information
        return ResponseEntity.ok(dto);
    }
}
