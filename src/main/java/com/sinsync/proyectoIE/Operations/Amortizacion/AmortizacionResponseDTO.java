package com.sinsync.proyectoIE.Operations.Amortizacion;

import java.util.List;

public record AmortizacionResponseDTO(
        String metodo,
        List<CuotaDTO> tabla
) {
}
