package com.sinsync.proyectoIE.Authentication;

import com.sinsync.proyectoIE.Users.UsersEntity;
import com.sinsync.proyectoIE.Users.UsersRepository;
import com.sinsync.proyectoIE.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    public AuthenticationResponse login(AuthenticationRequest authRequest) {
        try {
            UsersEntity user = usersRepository.findByIdUser(authRequest.identification())
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

            if (!passwordEncoder.matches(authRequest.password(), user.getPassword())) {
                throw new BadCredentialsException("Contraseña incorrecta");
            }

            String jwt = jwtService.generateToken(user, generateExtraClaims(user));

            return new AuthenticationResponse(jwt, user.getIdUser());

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error en autenticación: " + e.getMessage());
        }
    }

    private Map<String, Object> generateExtraClaims(UsersEntity user){
        Map<String,Object> extraClaims= new HashMap<>();
        extraClaims.put("id",user.getIdUser());
        extraClaims.put("name", user.getName());
        extraClaims.put("cell", user.getCellPhone());
        return extraClaims;
    }
}
