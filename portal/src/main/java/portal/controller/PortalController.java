package portal.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import portal.service.UserService;

@RestController
@RequestMapping("/")
public class PortalController {

    private final UserService userService;

    public PortalController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String role) {
        return userService.login(username, password, role);
    }
}
