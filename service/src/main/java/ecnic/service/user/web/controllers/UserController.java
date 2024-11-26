package ecnic.service.user.web.controllers;

import static ecnic.service.common.constants.CommonPathConstant.API_V1;
import static ecnic.service.common.constants.CommonPathConstant.CREATE;
import static ecnic.service.user.constants.UserPathConstant.AUTHN_CHECK_USERNAME;
import static ecnic.service.user.constants.UserPathConstant.AUTHN_LOGIN;
import static ecnic.service.user.constants.UserPathConstant.USERS;

import ecnic.service.user.UserService;
import ecnic.service.user.domain.models.CreateUser;
import ecnic.service.user.domain.models.CreateUserDTO;
import ecnic.service.user.domain.models.User;
import ecnic.service.common.security.UserCredential;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(API_V1 + USERS)
@RequiredArgsConstructor
class UserController {
    
    private final UserService userService;
    
    @GetMapping(value = AUTHN_LOGIN)
    public ResponseEntity<UserCredential> login(
            @RequestParam String username,
            @RequestParam String password
    ) {
        return ResponseEntity.ok(userService.login(username, password));
    }
    
    @GetMapping(value = AUTHN_CHECK_USERNAME + "{username}")
    public ResponseEntity<Boolean> checkUsername(
            @PathVariable String username
    ) {
        UserCredential userCredential = userService.findUsername(username);
        return Objects.nonNull(userCredential) ?
                ResponseEntity.ok(true)
                : ResponseEntity.notFound().build();
    }
    
    @PostMapping(value = CREATE)
    public ResponseEntity<List<User>> createUser(
            @RequestBody List<CreateUserDTO> createUsers
    ) {
        return ResponseEntity.ok(userService.createUser(createUsers));
    }
    
}
