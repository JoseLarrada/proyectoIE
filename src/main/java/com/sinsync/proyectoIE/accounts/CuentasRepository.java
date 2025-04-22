package com.sinsync.proyectoIE.accounts;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CuentasRepository extends JpaRepository<CuentasEntity,String> {
    List<CuentasEntity> findByCedulaAndEstadoCuentaTrue(String cedula);
}
