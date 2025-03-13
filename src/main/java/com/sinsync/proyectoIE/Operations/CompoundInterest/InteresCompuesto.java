package com.sinsync.proyectoIE.Operations.CompoundInterest;

import com.sinsync.proyectoIE.Operations.Intereses;

public class InteresCompuesto extends Intereses {
    public InteresCompuesto(Double tasaInteres, Double tiempo, Double valorFinal, Double valorPresente) {
        super(tasaInteres, tiempo, valorFinal, valorPresente);
    }

    public double calcularMonto() {
        return getValorPresente() * Math.pow((1 + getTasaInteres()), getTiempo()); // VF = C * (1+i)^t
    }

    public double calcularTiempo(double montoFinal) {
        return (Math.log(montoFinal) - Math.log(getValorPresente())) / Math.log(1 + getTasaInteres());
    }

    public double calcularInteres(double montoFinal) {
        return Math.pow(montoFinal / getValorPresente(), 1.0 / getTiempo()) - 1;
    }

    public double calcularCapital(double montoFinal) {
        return montoFinal / Math.pow((1 + getTasaInteres()), getTiempo());
    }
}
