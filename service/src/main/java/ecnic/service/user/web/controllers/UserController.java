package ecnic.service.user.web.controllers;

import ecnic.service.common.models.PagedResult;
import ecnic.service.user.UserService;
import ecnic.service.user.domain.models.CreateUser;
import ecnic.service.user.domain.models.UpdateUser;
import ecnic.service.user.domain.models.User;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
class UserController {
    
    private final UserService userService;
    
    @GetMapping("test1")
    String testApi() {
        return "Hello World";
    }
    
    @GetMapping("test2")
    String testApi2(@PathVariable String name) {
        return "Hello " + name;
    }
    
    @GetMapping("test3")
    String testApi3(@RequestParam String name, @RequestParam Integer version) {
        return "Hello " + name;
    }
    
    @GetMapping("test4")
    String testApi3(@RequestBody CreateUser createUser) {
        return "Hello " + createUser;
    }
    
    @GetMapping
    PagedResult<User> getUsers(@PageableDefault(value = 2, page = 0) Pageable pageable) {
        return userService.getUsers(pageable);
    }
    
    @GetMapping("{id}")
    User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    List<User> create(@RequestBody @Valid List<CreateUser> createUser) {
        return userService.createUser(createUser);
    }
    
    @PutMapping
    List<User> update(@RequestBody List<UpdateUser> updateUser) {
        return userService.updateUser(updateUser);
    }
    
    @DeleteMapping("{id}")
    void delete(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
