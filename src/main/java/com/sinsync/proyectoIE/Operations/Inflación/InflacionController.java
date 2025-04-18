package com.sinsync.proyectoIE.Operations.Inflación;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inflacion")
public class InflacionController {
    private final InflacionService inflacionService;

    @PostMapping
    public ResponseEntity<Double> calcularInflacion(@RequestBody InflacionRequestDTO request){
        try{
            return ResponseEntity.ok(inflacionService.calcular(request));
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
    }

}
