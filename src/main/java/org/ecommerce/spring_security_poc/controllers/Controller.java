package org.ecommerce.spring_security_poc.controllers;

import org.ecommerce.spring_security_poc.models.User;
import org.ecommerce.spring_security_poc.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public Controller(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/admin/protected")
    public String getProtected() {
        return "Protected resource";
    }

    @GetMapping("/public")
    public String getPublic() {
        return "Public resource";
    }

    @PostMapping("/auth/signin")
    public String signIn(@RequestBody User userInfo) {
        String username = userInfo.getUsername();
        String password = userInfo.getPassword();

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        return "OK";
    }

    @PostMapping("/auth/signup")
    public void signUp(@RequestBody User userInfo) {
        String username = userInfo.getUsername();
        String password = userInfo.getPassword();

        User user = new User(username, passwordEncoder.encode(password));
        userRepository.save(user);
    }
}