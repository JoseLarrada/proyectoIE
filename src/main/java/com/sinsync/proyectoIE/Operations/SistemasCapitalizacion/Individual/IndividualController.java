package com.sinsync.proyectoIE.Operations.SistemasCapitalizacion.Individual;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/individual")
public class IndividualController {
    private final IndividualService individualService;

    @PostMapping
    public ResponseEntity<Double> calcularIndividual(@RequestBody IndividualRequestDTO request){
        try{
            return ResponseEntity.ok(individualService.calcularIndividual(request));
        }catch (Exception e){
            return ResponseEntity.noContent().build();
        }
    }
}
