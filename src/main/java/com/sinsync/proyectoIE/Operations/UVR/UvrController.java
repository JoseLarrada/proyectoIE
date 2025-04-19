package com.sinsync.proyectoIE.Operations.UVR;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/uvr")
@RequiredArgsConstructor
public class UvrController {
    private final UvrService uvrService;

    @PostMapping
    public ResponseEntity<Double> convertidorUvr(@RequestBody UVRConversionRequest request){
        try{
            return ResponseEntity.ok(uvrService.convertir(request));
        }catch (Exception e){
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/futura")
    public ResponseEntity<Double> uvrFuturo(@RequestBody UVRFutureRequest request){
        try{
            return ResponseEntity.ok(uvrService.calcularUvrFutura(request));
        }catch (Exception e){
            return ResponseEntity.noContent().build();
        }
    }

}
