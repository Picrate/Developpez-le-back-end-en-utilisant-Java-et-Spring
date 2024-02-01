package info.patriceallary.chatop.controllers;

import info.patriceallary.chatop.domain.dto.UserDto;
import info.patriceallary.chatop.domain.model.User;
import info.patriceallary.chatop.services.domain.UserService;
import info.patriceallary.chatop.services.dto.DtoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@SecurityScheme(
        name = "bearerToken",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class UserController {

    private final DtoService dtoService;
    private final UserService userService;

    public UserController(DtoService dtoService, UserService userService)
    {
        this.dtoService = dtoService;
        this.userService = userService;
    }

    @GetMapping("/{requestedId}")
    @Operation(summary = "User profile informations", description = "Show user profile informations", security = {
            @SecurityRequirement(name = "bearerToken")})
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
    public ResponseEntity<UserDto> getUserProfile(@PathVariable Integer requestedId){
        UserDto dto;
        // if user exists
        if (userService.getUserById(requestedId).isPresent()) {
            // Retrieve user datas & Translate to Dto
            Optional<User> optionalUser = userService.getUserById(requestedId);
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
