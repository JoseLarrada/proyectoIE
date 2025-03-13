package com.sinsync.proyectoIE.Operations.GradienteAritmetico;

import org.springframework.stereotype.Service;

@Service
public class GradienteAService {
    public double calcularValorPresente(GradienteRequest request) {
        double factorAnualidad = (1 - Math.pow(1 + request.tasaInteres(), -request.periodos())) / request.tasaInteres();
        double factorGradiente = (factorAnualidad - request.periodos() * Math.pow(1 + request.tasaInteres(), -request.periodos())) / request.tasaInteres();
        return request.primerPago() * factorAnualidad + request.incrementoPago() * factorGradiente;
    }

    public double calcularValorFuturo(GradienteRequest request) {
        double factorAnualidad = (Math.pow(1 + request.tasaInteres(), request.periodos()) - 1) / request.tasaInteres();
        double factorGradiente = (factorAnualidad - request.periodos()) / request.tasaInteres();
        return request.primerPago() * factorAnualidad + request.incrementoPago() * factorGradiente;
    }
}
