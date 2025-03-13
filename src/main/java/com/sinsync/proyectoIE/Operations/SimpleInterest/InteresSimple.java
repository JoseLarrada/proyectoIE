package com.sinsync.proyectoIE.Operations.SimpleInterest;

import com.sinsync.proyectoIE.Operations.Intereses;
import lombok.Builder;


public class InteresSimple extends Intereses {
    public InteresSimple(Double tasaInteres, Double tiempo, Double valorFinal, Double valorPresente) {
        super(tasaInteres, tiempo, valorFinal, valorPresente);
    }

    public InteresSimple() {
    }

    protected double calcularInteres() {
        return getValorPresente() * getTasaInteres() * getTiempo(); // I = C * i * t
    }

    protected double calcularMonto() {
        return getValorPresente() * (1 + getTasaInteres() * getTiempo()); // VF = C(1 + i*t)
    }

    protected double calcularValorPresente(){
        return getValorFinal() / (1 + (getTasaInteres() * getTiempo()));
    }

    protected double calcularTasaDeInteres(){
        return ((getValorFinal()/getValorPresente()) - 1 / getTiempo());
    }

    protected double calcularTiempo(){
        return ((getValorFinal()/getValorPresente()) - 1 / getTasaInteres());
    }
}
