package info.patriceallary.chatop.controllers;

import info.patriceallary.chatop.domain.dto.UserDto;
import info.patriceallary.chatop.services.domain.UserService;
import info.patriceallary.chatop.services.dto.DtoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final DtoService dtoService;

    public UserController(DtoService dtoService) {
        this.dtoService = dtoService;
    }

    @GetMapping("/{requestdId}")
    public ResponseEntity<UserDto> getUserProfile(@PathVariable Integer requestedId){
        return ResponseEntity.ok(this.dtoService.getUserDtoForUserId(requestedId));
    }


}
