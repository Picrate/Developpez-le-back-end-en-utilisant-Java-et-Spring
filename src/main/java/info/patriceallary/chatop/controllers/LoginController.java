package info.patriceallary.chatop.controllers;

import info.patriceallary.chatop.services.JWTService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    private JWTService jwtService;

    private LoginController(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @GetMapping("/login")
    public String getString(Authentication authentication) {
        return "Plop !";
    }

    @PostMapping("/login")
    public String getToken(Authentication authentication) {
        return jwtService.generateToken(authentication);
    }


}
