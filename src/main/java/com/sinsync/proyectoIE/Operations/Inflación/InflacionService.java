package com.sinsync.proyectoIE.Operations.Inflación;

import org.springframework.stereotype.Service;

@Service
public class InflacionService {
    public double calcular(InflacionRequestDTO req) {
        String tipo = req.tipo().toUpperCase();
        double res;
        switch (tipo) {
            case "TASA REAL":
                // tasa real = (1 + nominal)/(1 + inflación) - 1
                res = (1 + req.tasaNominal()) / (1 + req.tasaInflacion()) - 1;
                break;
            case "TASA NOMINAL":
                // tasa nominal = (1 + real)*(1 + inflación) - 1
                res = (1 + req.tasaReal()) * (1 + req.tasaInflacion()) - 1;
                break;
            case "VALOR FUTURO":
                // valor futuro = valor * (1 + inflación)^periodos
                res = req.valor() * Math.pow(1 + req.tasaInflacion(), req.periodos());
                break;
            case "VALOR PRESENTE":
                // valor presente = valor / (1 + inflación)^periodos
                res = req.valor() / Math.pow(1 + req.tasaInflacion(), req.periodos());
                break;
            default:
                throw new IllegalArgumentException("Tipo de inflación no válido: " + req.tipo());
        }
        return res;
    }
}
