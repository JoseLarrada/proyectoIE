package com.sinsync.proyectoIE.Operations.TasaInteresRetorno.Modificada;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tirmodificada")
public class TirmController {
    private final TirmService tirmService;

    @PostMapping
    public ResponseEntity<TirmResponseDTO> calcularModificada(@RequestBody TirmRequestDTO request){
        try{
            return ResponseEntity.ok(tirmService.calcularTirm(request));
        }catch (Exception e){
            return ResponseEntity.noContent().build();
        }
    }
}
