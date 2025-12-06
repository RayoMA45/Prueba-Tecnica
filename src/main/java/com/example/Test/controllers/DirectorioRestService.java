package com.example.Test.controllers;

import com.example.Test.dto.PersonaDto;
import com.example.Test.dto.FacturaPersonaDto;
import com.example.Test.entities.Factura;
import com.example.Test.entities.Persona;
import com.example.Test.exceptions.ResponseObject;
import com.example.Test.services.Directorio;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/personas")
@Slf4j
public class DirectorioRestService {
    private final Directorio directorio;

    @GetMapping
    public ResponseObject getPersonas() {
        try {
            log.info("Consultando personas...");
            List<Persona> personas = directorio.findPersonas();

            List<PersonaDto> dtos = (personas != null ? personas.stream() : Stream.<Persona>empty())
                    .map(this::mapPersonaToDto)
                    .collect(Collectors.toList());

            Map<String, Object> data = Map.of("personas", dtos);
            return new ResponseObject(true, "Consulta de personas exitosa", data);
        } catch (Exception e) {
            log.error("Error inesperado al consultar personas: ", e);
            return new ResponseObject(false, "Ocurrió un error inesperado. Intente más tarde.", null);
        }
    }

    @GetMapping("/{identificacion}")
    public ResponseObject getPersona(@PathVariable String identificacion) {
        try {
            log.info("Consultando persona con identificacion: ", identificacion);
            Persona persona = directorio.findPersonaByIdentificacion(identificacion);
            if (persona == null) {
                log.warn("Persona no encontrada con identificacion: ", identificacion);
                return new ResponseObject(false, "Persona no encontrada con identificacion", null);
            }

            Map<String, Object> data = Map.of("persona", mapPersonaToDto(persona));
            return new ResponseObject(true, "Persona obtenida exitosamente", data);
        } catch (Exception e) {
            log.error("Error inesperado al consultar persona con identificacion: ", identificacion, e);
            return new ResponseObject(false, "Ocurrió un error inesperado. Intente más tarde.", null);
        }
    }

    @PostMapping
    public ResponseObject createPersona(@RequestBody Persona persona) {
        try {
            log.info("Creando persona: ", persona);
            Persona saved = directorio.storePersona(persona);

            Map<String, Object> data = Map.of("persona", mapPersonaToDto(saved));
            return new ResponseObject(true, "Persona creada exitosamente", data);
        } catch (Exception e) {
            log.error("Error inesperado al crear persona: ", e);
            return new ResponseObject(false, "Ocurrió un error al crear la persona", null);
        }
    }

    @DeleteMapping("/{identificacion}")
    public ResponseObject deletePersona(@PathVariable String identificacion) {
        try {
            log.info("Eliminando persona con identificacion: ", identificacion);
            directorio.deletePersonaByIdentificacion(identificacion);
            return new ResponseObject(true, "Persona eliminada exitosamente", null);
        } catch (Exception e) {
            log.error("Error inesperado al eliminar persona con identificacion : ", identificacion, e);
            return new ResponseObject(false, "Ocurrió un error al eliminar la persona", null);
        }
    }

    private PersonaDto mapPersonaToDto(Persona persona) {
        List<FacturaPersonaDto> facturas = new ArrayList<>();

        if (persona.getFacturas() != null) {
            for (Factura f : persona.getFacturas()) {
                facturas.add(FacturaPersonaDto.builder()
                        .id(f.getId())
                        .fecha(f.getFecha())
                        .monto(f.getMonto())
                        .build());
            }
        }

        return PersonaDto.builder()
                .id(persona.getId())
                .nombre(persona.getNombre())
                .apellidoPaterno(persona.getApellidoPaterno())
                .apellidoMaterno(persona.getApellidoMaterno())
                .identificacion(persona.getIdentificacion())
                .facturas(facturas)
                .build();
    }

    @GetMapping("/paginado")
    public ResponseObject getPersonasPaginadas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            log.info("Consultando personas paginadas");
            Pageable pageable = PageRequest.of(page, size);
            Page<Persona> personasPage = directorio.findPersonasPage(pageable);
            List<PersonaDto> dtos = personasPage.getContent().stream()
                    .map(this::mapPersonaToDto)
                    .collect(Collectors.toList());
            Map<String, Object> data = Map.of(
                    "personas", dtos,
                    "currentPage", personasPage.getNumber(),
                    "totalItems", personasPage.getTotalElements(),
                    "totalPages", personasPage.getTotalPages());
            return new ResponseObject(true, "Consulta de personas paginada exitosa", data);

        } catch (Exception e) {
            log.error("Error inesperado al consultar personas paginadas: ", e);
            return new ResponseObject(false, "Ocurrió un error inesperado. Intente más tarde.", null);
        }
    }

}
