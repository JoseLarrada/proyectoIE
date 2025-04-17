package com.sinsync.proyectoIE.Operations.TasaInteresRetorno.Modificada;

import org.springframework.stereotype.Service;

@Service
public class TirmService {
    public TirmResponseDTO calcularTirm(TirmRequestDTO req) {
        int n = req.periodos();
        double vfPos = 0, vaNeg = 0;
        for (CashFlowDTO cf : req.flujosPositivos()) {
            vfPos += cf.monto() * Math.pow(1 + req.tasaReinversion(), n - cf.periodo());
        }
        for (CashFlowDTO cf : req.flujosNegativos()) {
            vaNeg += cf.monto() / Math.pow(1 + req.tasaFinanciamiento(), cf.periodo());
        }
        if (vaNeg >= 0) throw new IllegalArgumentException("Flujos negativos erróneos");
        double tirm = Math.pow(vfPos / -vaNeg, 1.0 / n) - 1;
        return new TirmResponseDTO(tirm);
    }
}
