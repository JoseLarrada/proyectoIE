package com.sinsync.proyectoIE.Operations.SistemasCapitalizacion.Mixto;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mixto")
public class MixtoController {
    private final MixtoService mixtoService;

    @PostMapping
    public ResponseEntity<Double> calcularAmortizacionMixta(@RequestBody MixtaRequestDTO request){
        try{
            return ResponseEntity.ok(mixtoService.calcularMixto(request));
        }catch (Exception e){
            return ResponseEntity.noContent().build();
        }
    }
}
