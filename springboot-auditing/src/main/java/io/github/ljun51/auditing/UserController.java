package io.github.ljun51.auditing;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lijun (ljun51@outlook.com)
 * @date 2020-08-10
 */
@RestController
@RequestMapping()
public class UserController {

    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/user")
    public List<User> list() {
        return userRepository.findAll();
    }

    @PostMapping("/user")
    public User insert(@RequestBody User user) {
        return userRepository.save(user);
    }
}
