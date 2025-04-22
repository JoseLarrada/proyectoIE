package com.sinsync.proyectoIE.Loans;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "prestamos")
public class PrestamosEntity {
    @Id
    @Column(name = "id_prestamo")
    private String idPrestamo;
    private String cedula;
    @Column(name = "monto_prestamo")
    private Double montoPrestamo;
    private Double cuotas;
    @Column(name = "valor_cuota")
    private Double valorCuotas;
    @Column(name = "tipo_interes")
    private int tipoInteres;
    private Double intereses;
    private int estado;
    @Column(name = "fecha_solicitud")
    private Date fechaSolicitud;
    private int periodicidad;
    @Column(name = "metodo_calculo")
    private String metodoCalculo;
}
