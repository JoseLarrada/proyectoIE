package com.sinsync.proyectoIE.Operations.UVR;

import org.springframework.stereotype.Service;

@Service
public class UvrService {
    public double convertir(UVRConversionRequest request){
        double resultado;

        if (request.tipo().equalsIgnoreCase("uvr-a-pesos")) {
            resultado = request.valor() * request.uvrActual();
        } else if (request.tipo().equalsIgnoreCase("pesos-a-uvr")) {
            resultado = request.valor() / request.uvrActual();
        } else {
            throw new IllegalArgumentException("Tipo de conversión no válido.");
        }
        return resultado;
    }

    public double calcularUvrFutura(UVRFutureRequest request){
        return request.uvrActual() * Math.pow(1 +(request.ipc()/100), request.dias()/365.0);
    }
}
