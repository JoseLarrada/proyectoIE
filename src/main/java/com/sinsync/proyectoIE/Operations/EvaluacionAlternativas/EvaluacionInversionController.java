package com.sinsync.proyectoIE.Operations.EvaluacionAlternativas;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/evaluacion")
public class EvaluacionInversionController {
    private EvaluacionInversionService evaluacionService;

    @PostMapping
    public ResponseEntity<EvaluacionResponseDTO> evaluarInversiones(@RequestBody EvaluacionRequestDTO request){
        try{
            return ResponseEntity.ok(evaluacionService.evaluar(request));
        }catch (Exception e){
            return ResponseEntity.noContent().build();
        }
    }
}
