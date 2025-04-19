package com.sinsync.proyectoIE.Operations.TasaInteresRetorno.Real;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tirreal")
public class TasaRealController {
    private final TasaRealService tasaRealService;

    @PostMapping
    public ResponseEntity<Double> calcularReal(@RequestBody RealRequestDTO request){
        try{
            return ResponseEntity.ok(tasaRealService.calcularTasaReal(request));
        }catch (Exception e){
            return ResponseEntity.noContent().build();
        }
    }
}
