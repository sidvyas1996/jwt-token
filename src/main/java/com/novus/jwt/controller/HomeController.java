package com.novus.jwt.controller;

import com.novus.jwt.model.JwtRequest;
import com.novus.jwt.model.JwtResponse;
import com.novus.jwt.service.UserAuthenticationService;
import com.novus.jwt.util.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class HomeController {

    @Autowired
    private JWTUtility jwtUtility;
    @Autowired
    private UserAuthenticationService authenticationService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/")
    public String home(){
        return "Welcome to home page";
        }


    @PostMapping("/authenticate")
    public JwtResponse authenticate(@RequestBody JwtRequest request) throws Exception {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getUsername()
                    ,request.getPassword()));
        }

        catch (BadCredentialsException e)
        {
            throw new Exception("Invalid credentials",e);
        }


         final UserDetails userDetails
                 = authenticationService.loadUserByUsername(request.getUsername());
          final String token = jwtUtility.generateToken(userDetails);
          final Date date = new Date();
        System.out.println(token);
          return new JwtResponse(token,date);

     }

}
