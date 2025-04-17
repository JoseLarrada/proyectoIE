package com.sinsync.proyectoIE.Operations.SistemasCapitalizacion.Mixto;

import com.sinsync.proyectoIE.Operations.SistemasCapitalizacion.Colectiva.ColectivaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MixtoService {
    private final ColectivaService colectivaService;

    public double calcularMixto(MixtaRequestDTO request){
        double parteIndividual = request.porcentajeIndividual() * request.aporteIndividual().monto() * Math.pow(1 + request.aportesColectivos().tasa(), request.aporteIndividual().tiempo());
        double fondoColectivo = colectivaService.calcularColectiva(request.aportesColectivos());
        double parteColectiva = (1 - request.porcentajeIndividual()) * fondoColectivo;
        return parteIndividual + parteColectiva;
    }
}
