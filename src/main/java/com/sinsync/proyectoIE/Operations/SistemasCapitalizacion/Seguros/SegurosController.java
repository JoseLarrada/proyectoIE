package com.sinsync.proyectoIE.Operations.SistemasCapitalizacion.Seguros;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seguros")
public class SegurosController {

    private final SegurosService segurosService;

    @PostMapping
    public ResponseEntity<ResponseSegurosDTO> calcurarAmortizacionSeguros(@RequestBody SegurosDTO request){
        try{
            return ResponseEntity.ok(segurosService.calcularCapitalYRentabilidad(request));
        }catch (Exception e){
            return ResponseEntity.noContent().build();
        }
    }
}
