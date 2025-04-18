package com.sinsync.proyectoIE.Operations.Bonos;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bonos")
public class BonoController {
    private final BonoService bonoService;

    @PostMapping
    public ResponseEntity<BonoResponseDTO> calcularBono(@RequestBody BonoRequestDTO bonoRequestDTO){
        try{
            return ResponseEntity.ok(bonoService.evaluar(bonoRequestDTO));
        }catch (Exception e){
            return ResponseEntity.noContent().build();
        }
    }
}
