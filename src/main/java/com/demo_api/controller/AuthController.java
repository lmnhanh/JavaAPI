package com.demo_api.controller;

import com.demo_api.assembler.UserModelAssembler;
import com.demo_api.entity.UserEntity;
import com.demo_api.model.JwtResponse;
import com.demo_api.model.LoginRequest;
import com.demo_api.model.MessageResponse;
import com.demo_api.model.SignUpRequest;
import com.demo_api.security.jwt.JwtUtils;
import com.demo_api.service.UserService;
import com.demo_api.security.UserDetailsImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    UserService userService;
    @Autowired
    UserModelAssembler assembler;
    @Autowired
    JwtUtils jwtUtils;

    @PostMapping(value = "/signin")
    public ResponseEntity<?> signin(@RequestBody LoginRequest loginModel){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginModel.getUsername(), loginModel.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> privileges = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority()).collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), privileges));
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<?> signup(@RequestBody @NonNull SignUpRequest registerModel){
        if (userService.isUsernameExist(registerModel.getUsername())) {
            return new ResponseEntity(new MessageResponse("ERROR: Username đã được sử dụng!"), HttpStatus.BAD_REQUEST);
        }

        if (userService.isEmailExist(registerModel.getEmail())) {
            return new ResponseEntity(new MessageResponse("ERROR: Email đã được sử dụng!"), HttpStatus.BAD_REQUEST);
        }
        UserEntity user = new UserEntity(registerModel.getUsername(), registerModel.getEmail(), encoder.encode(registerModel.getPassword()));
        userService.addWithRoleUser(user);
        return new ResponseEntity(assembler.toModel(user), HttpStatus.OK);
    }
}
