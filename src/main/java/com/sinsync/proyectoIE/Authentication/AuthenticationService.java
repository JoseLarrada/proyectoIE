package com.sinsync.proyectoIE.Authentication;

import com.sinsync.proyectoIE.Users.UsersEntity;
import com.sinsync.proyectoIE.Users.UsersRepository;
import com.sinsync.proyectoIE.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private JwtService jwtService;

    public AuthenticationResponse login(AuthenticationRequest authRequest){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                authRequest.identification(),authRequest.password()
        );
        UsersEntity user = usersRepository.findByIdUser(authRequest.identification()).get();

        String jwt = jwtService.generateToken(user, generateExtraClaims(user));
        return new AuthenticationResponse(jwt, user.getIdUser());
    }

    private Map<String, Object> generateExtraClaims(UsersEntity user){
        Map<String,Object> extraClaims= new HashMap<>();
        extraClaims.put("id",user.getIdUser());
        extraClaims.put("name", user.getName());
        extraClaims.put("cell", user.getCellPhone());
        return extraClaims;
    }
}
