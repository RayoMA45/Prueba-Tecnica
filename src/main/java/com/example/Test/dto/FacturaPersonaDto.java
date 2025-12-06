package com.example.Test.dto;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacturaPersonaDto {
    private Long id;
    private LocalDate fecha;
    private double monto;
}
