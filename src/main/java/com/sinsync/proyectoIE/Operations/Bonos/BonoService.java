package com.sinsync.proyectoIE.Operations.Bonos;

import org.springframework.stereotype.Service;

@Service
public class BonoService {
    public BonoResponseDTO evaluar(BonoRequestDTO req) {
        int periodos = req.años() * req.frecuencia().getPeriodosPorAnio();
        double tasaPeriodica = req.tasaMercado() / req.frecuencia().getPeriodosPorAnio();
        double cupónPeriodico = req.valorNominal() * req.tasaCupon() / req.frecuencia().getPeriodosPorAnio();

        double precio = calcularPrecio(req.valorNominal(), cupónPeriodico, tasaPeriodica, periodos);
        double ytm = calcularYTM(req.precioMercado(), req.valorNominal(), cupónPeriodico, periodos);
        double duracion = calcularDuracion(req.valorNominal(), cupónPeriodico, tasaPeriodica, periodos, precio);
        double convexidad = calcularConvexidad(req.valorNominal(), cupónPeriodico, tasaPeriodica, periodos, precio);

        return new BonoResponseDTO(precio, ytm, duracion, convexidad);
    }

    private double calcularPrecio(double valorNominal, double cupón, double tasa, int n) {
        double precio = 0;
        for (int t = 1; t <= n; t++) {
            precio += cupón / Math.pow(1 + tasa, t);
        }
        precio += valorNominal / Math.pow(1 + tasa, n);
        return precio;
    }

    private double calcularYTM(double precioMercado, double valorNominal, double cupón, int n) {
        double r = 0.05;
        for (int i = 0; i < 1000; i++) {
            double f = 0, df = 0;
            for (int t = 1; t <= n; t++) {
                f += cupón / Math.pow(1 + r, t);
                df += -cupón * t / Math.pow(1 + r, t + 1);
            }
            f += valorNominal / Math.pow(1 + r, n);
            df += -valorNominal * n / Math.pow(1 + r, n + 1);
            f -= precioMercado;
            double siguiente = r - f / df;
            if (Math.abs(siguiente - r) < 1e-6) {
                return siguiente * 100;
            }
            r = siguiente;
        }
        return r * 100;
    }

    private double calcularDuracion(double valorNominal, double cupón, double tasa, int n, double precio) {
        double dur = 0;
        for (int t = 1; t <= n; t++) {
            dur += t * cupón / Math.pow(1 + tasa, t);
        }
        dur += n * valorNominal / Math.pow(1 + tasa, n);
        return dur / precio;
    }

    private double calcularConvexidad(double valorNominal, double cupón, double tasa, int n, double precio) {
        double conv = 0;
        for (int t = 1; t <= n; t++) {
            conv += cupón * t * (t + 1) / Math.pow(1 + tasa, t + 2);
        }
        conv += valorNominal * n * (n + 1) / Math.pow(1 + tasa, n + 2);
        return conv / precio;
    }
}
