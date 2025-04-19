package com.sinsync.proyectoIE.Operations.TasaInteresRetorno.Simple;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tirsimple")
public class TirController {
    private final TirService tirService;

    @PostMapping
    public ResponseEntity<TirResponseDTO> calcularTir(@RequestBody TirRequestDTO request){
        try{
            return ResponseEntity.ok(tirService.calcularTIR(request));
        }catch (Exception e){
            return ResponseEntity.noContent().build();
        }
    }
}
