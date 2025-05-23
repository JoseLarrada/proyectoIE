package com.sinsync.proyectoIE.Operations.Amortizacion;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AmortizacionService {

    public AmortizacionResponseDTO calcularFrances(AmortizacionRequestDTO req) {
        double P = req.monto();
        double r = req.tasa();
        int n = req.periodos();
        double cuota = (P * r) / (1 - Math.pow(1 + r, -n));
        double saldo = P;
        List<CuotaDTO> tabla = new ArrayList<>();

        for (int t = 1; t <= n; t++) {
            double interes = saldo * r;
            double amortizacion = cuota - interes;
            saldo -= amortizacion;
            tabla.add(new CuotaDTO(t, cuota, interes, amortizacion, Math.max(saldo, 0)));
        }

        return new AmortizacionResponseDTO(tabla);
    }

    public AmortizacionResponseDTO calcularAleman(AmortizacionRequestDTO req) {
        double P = req.monto();
        double r = req.tasa();
        int n = req.periodos();
        double amortizacion = P / n;
        double saldo = P;
        List<CuotaDTO> tabla = new ArrayList<>();

        for (int t = 1; t <= n; t++) {
            double interes = saldo * r;
            double cuota = amortizacion + interes;
            saldo -= amortizacion;
            tabla.add(new CuotaDTO(t, cuota, interes, amortizacion, Math.max(saldo, 0)));
        }

        return new AmortizacionResponseDTO(tabla);
    }

    public AmortizacionResponseDTO calcularAmericano(AmortizacionRequestDTO req) {
        double P = req.monto();
        double r = req.tasa();
        int n = req.periodos();
        double interes = P * r;
        double saldo = P;
        List<CuotaDTO> tabla = new ArrayList<>();

        for (int t = 1; t <= n; t++) {
            double cuota = (t == n) ? interes + P : interes;
            double amortizacion = (t == n) ? P : 0;
            saldo = (t == n) ? 0 : saldo;
            tabla.add(new CuotaDTO(t, cuota, interes, amortizacion, saldo));
        }

        return new AmortizacionResponseDTO(tabla);
    }
}
