package com.sinsync.proyectoIE.Operations.TasaInteresRetorno.Simple;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TirService {
    public TirResponseDTO calcularTIR(TirRequestDTO request) {
        List<Double> flujos = request.flujos();
        double tir = aproximarTIR(flujos);
        return new TirResponseDTO(tir);
    }

    private double aproximarTIR(List<Double> flujos) {
        double r = 0.1; // tasa inicial 10%
        double precision = 1e-6;
        double delta = 1e-6;
        int maxIteraciones = 1000;

        for (int i = 0; i < maxIteraciones; i++) {
            double vpn = calcularVPN(flujos, r);
            double vpnDelta = calcularVPN(flujos, r + delta);
            double derivada = (vpnDelta - vpn) / delta;

            if (Math.abs(derivada) < precision) break; // evitar división por cero

            double rNueva = r - vpn / derivada;

            if (Math.abs(rNueva - r) < precision) {
                return rNueva;
            }

            r = rNueva;
        }

        throw new RuntimeException("No se pudo calcular la TIR");
    }

    private double calcularVPN(List<Double> flujos, double tasa) {
        double vpn = 0.0;
        for (int t = 0; t < flujos.size(); t++) {
            vpn += flujos.get(t) / Math.pow(1 + tasa, t);
        }
        return vpn;
    }

}
