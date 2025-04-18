package com.sinsync.proyectoIE.Operations.EvaluacionAlternativas;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class EvaluacionInversionService {
    public EvaluacionResponseDTO evaluar(EvaluacionRequestDTO request) {
        List<ResultadoEvaluacionDTO> resultados = new ArrayList<>();

        for (FlujoAlternativaDTO alt : request.alternativas()) {
            double vpn = calcularVPN(alt.flujos(), alt.inversionInicial(), request.tasaDescuento());
            double tir = calcularTIR(alt.flujos(), alt.inversionInicial());
            double pri = calcularPRI(alt.flujos(), alt.inversionInicial());
            double vau = calcularVAU(vpn, alt.flujos().size(), request.tasaDescuento());
            double bc = calcularBeneficioCosto(alt.flujos(), request.tasaDescuento(), alt.inversionInicial());

            ResultadoEvaluacionDTO res = new ResultadoEvaluacionDTO(
                    alt.nombre(), vpn, tir, pri, vau, bc
            );

            resultados.add(res);
        }
        return new EvaluacionResponseDTO(resultados, generarRecomendacion(resultados));
    }

    private double calcularVPN(List<Double> flujos, double inversionInicial, double tasa) {
        double vpn = -inversionInicial;
        for (int t = 0; t < flujos.size(); t++) {
            vpn += flujos.get(t) / Math.pow(1 + tasa, t + 1);
        }
        return vpn;
    }

    private double calcularTIR(List<Double> flujos, double inversionInicial) {
        double r = 0.1;
        for (int i = 0; i < 1000; i++) {
            double f = -inversionInicial;
            double df = 0;
            for (int t = 0; t < flujos.size(); t++) {
                f += flujos.get(t) / Math.pow(1 + r, t + 1);
                df += -flujos.get(t) * (t + 1) / Math.pow(1 + r, t + 2);
            }
            double rNext = r - f / df;
            if (Math.abs(rNext - r) < 1e-6) return rNext * 100;
            r = rNext;
        }
        return r * 100;
    }

    private double calcularPRI(List<Double> flujos, double inversionInicial) {
        double acumulado = 0;
        for (int t = 0; t < flujos.size(); t++) {
            acumulado += flujos.get(t);
            if (acumulado >= inversionInicial) {
                double faltante = inversionInicial - (acumulado - flujos.get(t));
                return t + (faltante / flujos.get(t));
            }
        }
        return -1; // No se recupera
    }

    private double calcularVAU(double vpn, int n, double tasa) {
        return vpn * ((tasa * Math.pow(1 + tasa, n)) / (Math.pow(1 + tasa, n) - 1));
    }

    private double calcularBeneficioCosto(List<Double> flujos, double tasa, double inversionInicial) {
        double beneficios = 0;
        for (int t = 0; t < flujos.size(); t++) {
            beneficios += flujos.get(t) / Math.pow(1 + tasa, t + 1);
        }
        return beneficios / inversionInicial;
    }

    private String generarRecomendacion(List<ResultadoEvaluacionDTO> resultados) {
        ResultadoEvaluacionDTO mejor = resultados.stream().max(Comparator.comparing(ResultadoEvaluacionDTO::vpn)).orElse(null);
        if (mejor != null) {
            return "La mejor alternativa es " + mejor.nombre() + " con un VPN de " + mejor.vpn();
        }
        return "No se pudo determinar la mejor alternativa.";
    }
}
