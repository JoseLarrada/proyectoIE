package com.sinsync.proyectoIE.Operations.Amortizacion;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/amortizacion")
public class AmortizacionController {
    private final AmortizacionService amortizacionService;

    @PostMapping("/francesa")
    public ResponseEntity<AmortizacionResponseDTO> amortizacionFrancesa(@RequestBody AmortizacionRequestDTO requestDTO){
        try{
            return ResponseEntity.ok(amortizacionService.calcularFrances(requestDTO));
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/alemana")
    public ResponseEntity<AmortizacionResponseDTO> amortizacionAlemana(@RequestBody AmortizacionRequestDTO requestDTO){
        try{
            return ResponseEntity.ok(amortizacionService.calcularAleman(requestDTO));
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/americana")
    public ResponseEntity<AmortizacionResponseDTO> amortizacionAmericana(@RequestBody AmortizacionRequestDTO requestDTO){
        try{
            return ResponseEntity.ok(amortizacionService.calcularAmericano(requestDTO));
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
}
