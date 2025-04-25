package com.sinsync.proyectoIE.Operations.TasaInteresRetorno.Contable;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tircontable")
@RequiredArgsConstructor
public class RoiController {
    private final RoiService roiService;

    @PostMapping
    public ResponseEntity<Double> calcularRoi(@RequestBody RoiRequestDTO request){
        try{
            return ResponseEntity.ok(roiService.calcularRoi(request));
        }catch (Exception e){
            return ResponseEntity.noContent().build();
        }
    }
}
