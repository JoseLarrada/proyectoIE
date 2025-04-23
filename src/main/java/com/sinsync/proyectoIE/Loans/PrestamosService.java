package com.sinsync.proyectoIE.Loans;

import com.sinsync.proyectoIE.Doc.GeneratorId;
import com.sinsync.proyectoIE.Operations.Amortizacion.AmortizacionRequestDTO;
import com.sinsync.proyectoIE.Operations.Amortizacion.AmortizacionService;
import com.sinsync.proyectoIE.Operations.Amortizacion.CuotaDTO;
import com.sinsync.proyectoIE.Operations.CompoundInterest.CompuestoService;
import com.sinsync.proyectoIE.Operations.CompoundInterest.InteresCompuesto;
import com.sinsync.proyectoIE.Operations.GradienteAritmetico.GradienteAService;
import com.sinsync.proyectoIE.Operations.GradienteAritmetico.GradienteRequest;
import com.sinsync.proyectoIE.Operations.SimpleInterest.InteresSimple;
import com.sinsync.proyectoIE.Operations.SimpleInterest.SimpleService;
import com.sinsync.proyectoIE.Operations.gradienteGeometrico.GradienteGRequest;
import com.sinsync.proyectoIE.Operations.gradienteGeometrico.GradienteGeometricoService;
import com.sinsync.proyectoIE.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PrestamosService {
    private final PrestamosRepository prestamosRepository;
    private final AmortizacionService amortizacionService;
    private final GradienteAService gradienteAService;
    private final GradienteGeometricoService gradienteGeometricoService;
    private final CompuestoService compuestoService;
    private final SimpleService simpleService;
    private final GeneratorId generatorId;
    private final JwtService jwtService;

    public ResponseEntity<String> registrarPrestamo(CrearPrestamoDTO dto, HttpServletRequest requestHeader) {
        try {
            ResultadoCalculo resultado;

            switch (dto.metodoCalculo().toUpperCase()) {
                case "SIMPLE" -> resultado = calcularSimple(dto);
                case "COMPUESTO" -> resultado = calcularCompuesto(dto);
                case "FRANCES" -> resultado = calcularFrances(dto);
                case "ALEMAN" -> resultado = calcularAleman(dto);
                case "GRADIENTE ARITMETICO" -> resultado = calcularGradienteAritmetico(dto);
                case "GRADIENTE GEOMETRICO" -> resultado = calcularGradienteGeometrico(dto);
                default -> {
                    return ResponseEntity.badRequest().body("Método de cálculo no válido");
                }
            }

            PrestamosEntity prestamo = PrestamosEntity.builder()
                    .idPrestamo(generatorId.IdGenerator())
                    .cedula(jwtService.extractID(jwtService.extraerTokenDesdeHeader(requestHeader)))
                    .montoPrestamo(dto.montoPrestamo())
                    .cuotas(dto.cuotas())
                    .valorCuotas(resultado.valorCuota())
                    .valorTotalPagar(resultado.valorTotalPagar())
                    .tipoInteres(dto.tipoInteres())
                    .intereses(resultado.intereses())
                    .estado(0)
                    .fechaSolicitud(new Date())
                    .periodicidad(dto.periodicidad())
                    .metodoCalculo(dto.metodoCalculo())
                    .build();

            prestamosRepository.save(prestamo);
            return ResponseEntity.ok("Préstamo registrado correctamente");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al registrar el préstamo: " + e.getMessage());
        }
    }

    public ResponseEntity<List<PrestamosEntity>> obtenerPrestamosPorUsuario(HttpServletRequest request) {
        try {
            String token = jwtService.extraerTokenDesdeHeader(request);
            String cedula = jwtService.extractID(token);
            List<PrestamosEntity> prestamos = prestamosRepository.findByCedulaOrderByFechaSolicitudDesc(cedula);

            return ResponseEntity.ok(prestamos);

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    private ResultadoCalculo calcularSimple(CrearPrestamoDTO dto) {
        double tiempo = calcularTiempoReal(dto.cuotas(), dto.periodicidad());
        var response = simpleService.calcularInteresSimple(
                new InteresSimple(dto.tasa(), tiempo, 0.0, dto.montoPrestamo())
        );
        double total = response.monto();
        return new ResultadoCalculo(total - dto.montoPrestamo(), total / dto.cuotas(), total);
    }

    private ResultadoCalculo calcularCompuesto(CrearPrestamoDTO dto) {
        double tiempo = calcularTiempoReal(dto.cuotas(), dto.periodicidad());
        var response = compuestoService.calcularInteresCompuesto(
                new InteresCompuesto(dto.tasa(), tiempo, 0.0, dto.montoPrestamo())
        );
        double total = response.monto();
        return new ResultadoCalculo(total - dto.montoPrestamo(), total / dto.cuotas(), total);
    }

    private ResultadoCalculo calcularFrances(CrearPrestamoDTO dto) {
        var tablaResultado = amortizacionService.calcularFrances(new AmortizacionRequestDTO(dto.montoPrestamo(), dto.tasa(), dto.cuotas().intValue()));
        double cuota = tablaResultado.tabla().getFirst().cuota();
        double total = cuota * dto.cuotas();
        return new ResultadoCalculo(total - dto.montoPrestamo(), cuota, total);
    }

    private ResultadoCalculo calcularAleman(CrearPrestamoDTO dto) {
        var tablaResultado = amortizacionService.calcularAleman(new AmortizacionRequestDTO(dto.montoPrestamo(), dto.tasa(), dto.cuotas().intValue()));
        double total = tablaResultado.tabla().stream().mapToDouble(CuotaDTO::cuota).sum();
        double cuota = tablaResultado.tabla().getFirst().cuota();
        return new ResultadoCalculo(total - dto.montoPrestamo(), cuota, total);
    }

    private ResultadoCalculo calcularGradienteAritmetico(CrearPrestamoDTO dto) {
        var request = new GradienteRequest(dto.montoPrestamo(), 0.0, dto.tasa(), dto.cuotas());
        double total = gradienteAService.calcularValorFuturo(request);
        return new ResultadoCalculo(total - dto.montoPrestamo(), total / dto.cuotas(), total);
    }

    private ResultadoCalculo calcularGradienteGeometrico(CrearPrestamoDTO dto) {
        var request = new GradienteGRequest(dto.montoPrestamo(), dto.tasa(), 0.05, dto.cuotas().intValue());
        double total = gradienteGeometricoService.calcularValorFuturo(request);
        return new ResultadoCalculo(total - dto.montoPrestamo(), total / dto.cuotas(), total);
    }

    private double calcularTiempoReal(double cuotas, int periodicidadId) {
        return switch (periodicidadId) {
            case 1 -> cuotas;        // MENSUAL → 1 cuota = 1 mes
            case 2 -> cuotas * 2;    // BIMESTRAL → 1 cuota = 2 meses
            case 3 -> cuotas * 3;    // TRIMESTRAL → 1 cuota = 3 meses
            case 4 -> cuotas * 6;    // SEMESTRAL → 1 cuota = 6 meses
            case 5 -> cuotas * 12;   // ANUAL → 1 cuota = 12 meses
            default -> cuotas;       // Por defecto mensual
        };
    }

}
