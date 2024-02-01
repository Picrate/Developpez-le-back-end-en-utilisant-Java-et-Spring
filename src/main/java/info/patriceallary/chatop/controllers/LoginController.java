package info.patriceallary.chatop.controllers;

import info.patriceallary.chatop.domain.dto.*;
import info.patriceallary.chatop.domain.model.User;
import info.patriceallary.chatop.services.dto.DtoService;
import info.patriceallary.chatop.services.auth.JWTService;
import info.patriceallary.chatop.services.auth.LoginAndRegisterService;
import info.patriceallary.chatop.services.domain.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
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
@SecurityScheme(
        name = "bearerToken",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
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

    @PostMapping("/login")
    @Operation(description = "Login as a registred user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication Success", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TokenDto.class)
                    )
            }),
            @ApiResponse(responseCode = "401", description = "Authentication Failure", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            })
    })
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
    @Operation(description = "Register as a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Register as a new Success", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TokenDto.class)
                    )
            }),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied")})
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

    // My User Profile
    @GetMapping("/me")
    @Operation(summary = "My user profile informations",
            description = "Show my user profile informations",
            security = {@SecurityRequirement(name = "bearerToken")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "OK",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserDto.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = {
                            @Content()
                    }
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = {
                            @Content()
                    }
            )
    })
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
