package com.example.Test.controllers;

import com.example.Test.dto.FacturaDto;
import com.example.Test.dto.FacturaMapper;
import com.example.Test.entities.Factura;
import com.example.Test.entities.Persona;
import com.example.Test.exceptions.ResponseObject;
import com.example.Test.services.Ventas;
import com.example.Test.services.Directorio;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/facturas")
@Slf4j
public class FacturaRestService {
    private final Ventas ventas;

    private final Directorio directorio;

    @PostMapping
    public ResponseObject createFactura(@RequestBody Factura factura) {
        try {
            log.info("Creando factura: ", factura);
            Factura saved = ventas.storeFactura(factura);
            log.info("Factura creada con un id: ", factura.getId());
            ResponseEntity.ok(saved);
            return new ResponseObject(true, "Factura creada con exito", null);
        } catch (Exception e) {
            return new ResponseObject(false, "Error al registrar una nueva factura, Intentelo más tarde.", null);
        }
    }

    @GetMapping("/persona/{identificacion}")
    public ResponseObject getFacturasByPersona(@PathVariable String identificacion) {
        try {
            log.info("Buscando facturas para persona con identificacion: {}", identificacion);
            Persona persona = directorio.findPersonaByIdentificacion(identificacion);
            if (persona == null) {
                log.warn("Persona no encontrada: {}", identificacion);
                return new ResponseObject(false, "La persona con esa identificacion no fue encontrada", null);
            }
            List<Factura> facturas = ventas.findFacturasByPersona(persona);
            List<FacturaDto> dtos = facturas.stream()
                    .map(FacturaMapper::toDTO)
                    .toList();

            log.info("Facturas encontradas: {}", facturas.size());
            Map<String, Object> data = Map.of("facturas", dtos);
            return new ResponseObject(true, "Persona encontrada con éxito", data);
        } catch (Exception e) {
            log.error("Error inesperado en getFacturasByPersona: ", e);
            return new ResponseObject(false, "Ocurrió un error inesperado. Intente más tarde.", null);
        }
    }

}
