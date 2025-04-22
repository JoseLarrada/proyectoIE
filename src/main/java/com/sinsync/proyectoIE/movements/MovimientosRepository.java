package com.sinsync.proyectoIE.movements;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovimientosRepository extends JpaRepository<MovimientosEntity,String> {
    List<MovimientosEntity> findByIdCuentaOrderByFechaMovimientoDesc(String idCuenta);
}
