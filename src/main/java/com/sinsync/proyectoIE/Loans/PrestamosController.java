package com.sinsync.proyectoIE.Loans;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prestamos")
@RequiredArgsConstructor
public class PrestamosController {
    private final PrestamosService prestamosService;

    @PostMapping("/registrar")
    public ResponseEntity<String> registrarPrestamo(@RequestBody CrearPrestamoDTO dto, HttpServletRequest request) {
        return prestamosService.registrarPrestamo(dto, request);
    }

    @GetMapping("/mis-prestamos")
    public ResponseEntity<?> obtenerMisPrestamos(HttpServletRequest request) {
        return prestamosService.obtenerPrestamosPorUsuario(request);
    }
}
