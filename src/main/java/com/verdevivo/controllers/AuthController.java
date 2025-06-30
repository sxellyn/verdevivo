
package com.verdevivo.controllers;

import com.verdevivo.dto.LoginRequest;
import com.verdevivo.security.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenUtil tokenUtil;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
            )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = tokenUtil.generateToken(userDetails.getUsername());

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return response;
    }
}
