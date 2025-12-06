package com.example.Test.dto;

import com.example.Test.entities.Factura;

public class FacturaMapper {

    public static FacturaDto toDTO(Factura factura) {
        FacturaDto dto = new FacturaDto();
        dto.setId(factura.getId());
        dto.setFecha(factura.getFecha().toString());
        dto.setMonto(factura.getMonto());
        dto.setPersonaNombre(factura.getPersona().getNombre());
        dto.setPersonaIdentificacion(factura.getPersona().getIdentificacion());
        return dto;
    }
}
