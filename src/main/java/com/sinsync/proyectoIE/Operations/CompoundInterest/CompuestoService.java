package com.sinsync.proyectoIE.Operations.CompoundInterest;

import com.sinsync.proyectoIE.Operations.ResponseOperations;
import com.sinsync.proyectoIE.Operations.SimpleInterest.InteresSimple;
import org.springframework.stereotype.Service;

@Service
public class CompuestoService {
    public ResponseOperations calcularInteresCompuesto(InteresCompuesto interes) {
        if (interes == null) {
            throw new IllegalArgumentException("El objeto de interés compuesto no puede ser null.");
        }

        // Asignar valores predeterminados si son null
        Double tasaInteres = (interes.getTasaInteres() != null) ? interes.getTasaInteres() : 0.0;
        Double tiempo = (interes.getTiempo() != null) ? interes.getTiempo() : 0.0;
        Double valorFinal = (interes.getValorFinal() != null) ? interes.getValorFinal() : 0.0;
        Double valorPresente = (interes.getValorPresente() != null) ? interes.getValorPresente() : 0.0;

        // Crear una nueva instancia de InteresCompuesto con valores asegurados
        InteresCompuesto interesCompuesto = new InteresCompuesto(tasaInteres, tiempo, valorFinal, valorPresente);

        try {
            // Calcular el monto final o valor final
            if (valorFinal == 0.0 && valorPresente != 0.0 && tasaInteres != 0.0 && tiempo != 0.0) {
                valorFinal = interesCompuesto.calcularMonto();
            }

            // Calcular el tiempo
            if (tiempo == 0.0 && tasaInteres != 0.0 && valorFinal != 0.0 && valorPresente != 0.0) {
                tiempo = interesCompuesto.calcularTiempo(valorFinal);
            }

            // Calcular la tasa de interés
            if (tasaInteres == 0.0 && valorFinal != 0.0 && valorPresente != 0.0 && tiempo != 0.0) {
                tasaInteres = interesCompuesto.calcularInteres(valorFinal);
            }

            // Calcular el capital o valor presente
            if (valorFinal != 0.0 && tasaInteres != 0.0 && tiempo != 0.0) {
                valorPresente = interesCompuesto.calcularCapital(valorFinal);
            }

        } catch (NullPointerException e) {
            System.err.println("Error: Un valor inesperado es null - " + e.getMessage());
            throw new IllegalStateException("Error en el cálculo de interés compuesto debido a valores nulos.");
        }

        return new ResponseOperations(0.0, valorFinal, valorPresente, tasaInteres, tiempo);
    }
}
