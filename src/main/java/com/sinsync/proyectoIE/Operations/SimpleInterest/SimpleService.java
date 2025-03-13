package com.sinsync.proyectoIE.Operations.SimpleInterest;

import com.sinsync.proyectoIE.Operations.ResponseOperations;
import org.springframework.stereotype.Service;

@Service
public class SimpleService {
    public ResponseOperations calcularInteresSimple(InteresSimple interes) {
        if (interes == null) {
            throw new IllegalArgumentException("El objeto de interés simple no puede ser null.");
        }

        // Asignar valores predeterminados si son null
        Double tasaInteres = (interes.getTasaInteres() != null) ? interes.getTasaInteres() : 0.0;
        Double tiempo = (interes.getTiempo() != null) ? interes.getTiempo() : 0.0;
        Double valorFinal = (interes.getValorFinal() != null) ? interes.getValorFinal() : 0.0;
        Double valorPresente = (interes.getValorPresente() != null) ? interes.getValorPresente() : 0.0;
        Double interesSimpleCalculado = 0.0;

        // Crear una nueva instancia con valores asegurados
        InteresSimple interesSimple = new InteresSimple(tasaInteres, tiempo, valorFinal, valorPresente);

        try {
            // Calcular el monto final o valor final
            if (valorFinal == 0.0 && valorPresente != 0.0 && tasaInteres != 0.0 && tiempo != 0.0) {
                valorFinal = interesSimple.calcularMonto();
            }

            // Calcular la tasa de interés
            if (tasaInteres == 0.0 && tiempo != 0.0 && valorFinal != 0.0 && valorPresente != 0.0) {
                tasaInteres = interesSimple.calcularTasaDeInteres();
            }

            // Calcular el tiempo
            if (tiempo == 0.0 && valorFinal != 0.0 && valorPresente != 0.0 && tasaInteres != 0.0) {
                tiempo = interesSimple.calcularTiempo();
            }

            // Calcular el valor presente o capital
            if (valorPresente == 0.0 && valorFinal != 0.0 && tasaInteres != 0.0 && tiempo != 0.0) {
                valorPresente = interesSimple.calcularValorPresente();
            }

            // Calcular el interés simple
            if (valorPresente != 0.0 && tasaInteres != 0.0 && tiempo != 0.0) {
                interesSimpleCalculado = interesSimple.calcularInteres();
            }

        } catch (NullPointerException e) {
            System.err.println("Error: Un valor inesperado es null - " + e.getMessage());
            throw new IllegalStateException("Error en el cálculo de interés simple debido a valores nulos.");
        }

        return new ResponseOperations(interesSimpleCalculado, valorFinal, valorPresente, tasaInteres, tiempo);
    }

}
