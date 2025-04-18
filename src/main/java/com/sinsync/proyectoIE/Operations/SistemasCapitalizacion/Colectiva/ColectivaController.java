package com.sinsync.proyectoIE.Operations.SistemasCapitalizacion.Colectiva;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/colectiva")
public class ColectivaController {
    private final ColectivaService colectivaService;

    @PostMapping
    public ResponseEntity<Double> calcularAmortizacionColectiva(@RequestBody ColectivaRequestDTO requestDTO){
        try{
            return ResponseEntity.ok(colectivaService.calcularColectiva(requestDTO));
        }catch (Exception e){
            return ResponseEntity.noContent().build();
        }
    }
}
