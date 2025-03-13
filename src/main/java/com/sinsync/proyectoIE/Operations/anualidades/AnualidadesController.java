package com.sinsync.proyectoIE.Operations.anualidades;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/anualidades")
@RequiredArgsConstructor
public class AnualidadesController {
    private final AnualidadesService anualidades;

    @PostMapping("/valorFinal")
    public ResponseEntity<Double>  valorFinalAnualidadCierta(@RequestBody RequestAnualidad anualidad){
        return ResponseEntity.ok(anualidades.valorFinalAnualidadCierta(anualidad));
    }

    @PostMapping("/valorAtual")
    public ResponseEntity<Double>  valorActualAnualidadesSimple(@RequestBody RequestAnualidad anualidad){
        return ResponseEntity.ok(anualidades.valorActualAnualidadesSimple(anualidad));
    }
}
