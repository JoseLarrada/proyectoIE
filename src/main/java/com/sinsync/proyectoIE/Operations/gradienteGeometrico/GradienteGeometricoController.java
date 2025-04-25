package com.sinsync.proyectoIE.Operations.gradienteGeometrico;

import com.sinsync.proyectoIE.Operations.GradienteAritmetico.GradienteRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gradientegeometrico")
public class GradienteGeometricoController {
    private final GradienteGeometricoService gradienteGeometricoService;

    @PostMapping("/presente")
    public ResponseEntity<Double> calcularValorPresente(@RequestBody GradienteGRequest request){
        return ResponseEntity.ok(gradienteGeometricoService.calcularValorPresente(request));
    }

    @PostMapping("/futuro")
    public ResponseEntity<Double> calcularValorFuturo(@RequestBody GradienteGRequest request){
        return ResponseEntity.ok(gradienteGeometricoService.calcularValorFuturo(request));
    }
}
