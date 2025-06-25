package com.microservices.AuthServiceApp.Controller;

import com.microservices.AuthServiceApp.DTO.AuthResponse;
import com.microservices.AuthServiceApp.DTO.LoginRequest;
import com.microservices.AuthServiceApp.DTO.RegisterRequest;
import com.microservices.AuthServiceApp.Service.UserService;
import com.microservices.AuthServiceApp.Util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, UserService userService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request){
        userService.registerUser(
                request.getUsername(),
                request.getPassword(),
                request.getEmail(),
                Set.of("USER")
        );
        return ResponseEntity.ok("User Registered Successfully");
    }


    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
            )
        );

        String jwt = jwtUtil.generateToken(
                request.getUsername(),
                userService.loadUserByUsername(request.getUsername()).getAuthorities()
                        .stream()
                        .map(auth -> auth.getAuthority())
                        .collect(Collectors.toSet())
        );
        return ResponseEntity.ok(new AuthResponse(jwt));
    }


}
