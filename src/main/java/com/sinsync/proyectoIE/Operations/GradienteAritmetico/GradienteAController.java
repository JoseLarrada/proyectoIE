package com.sinsync.proyectoIE.Operations.GradienteAritmetico;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gradientes")
@RequiredArgsConstructor
public class GradienteAController {
    private final GradienteAService gradiente;

    @PostMapping("/presente")
    public ResponseEntity<GradienteResponse> calcularValorPresente(@RequestBody GradienteRequest request){
        return ResponseEntity.ok(gradiente.calcularValorPresente(request));
    }

    @PostMapping("/futuro")
    public ResponseEntity<GradienteResponse> calcularValorFuturo(@RequestBody GradienteRequest request){
        return ResponseEntity.ok(gradiente.calcularValorFuturo(request));
    }
}
