package com.sinsync.proyectoIE.Operations.SimpleInterest;

import com.sinsync.proyectoIE.Operations.ResponseOperations;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/simple")
public class SimpleController {
    private final SimpleService simpleService;

    @PostMapping
    public ResponseEntity<ResponseOperations> obtenerInteresSimple(@RequestBody InteresSimple request){
        return ResponseEntity.ok(simpleService.calcularInteresSimple(request));
    }
}
