package com.example.Test.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacturaDto {
    private Long id;
    private String fecha;
    private double monto;
    private String personaNombre;
    private String personaIdentificacion;
}
