    package com.example.Test.controllers;

    import com.example.Test.dto.FacturaDto;
import com.example.Test.dto.FacturaMapper;
    import com.example.Test.entities.Factura;
    import com.example.Test.entities.Persona;
    import com.example.Test.services.Ventas;
    import com.example.Test.services.Directorio;
    import lombok.RequiredArgsConstructor;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;

    @RestController
    @RequiredArgsConstructor
    @RequestMapping("/facturas")
    public class FacturaRestService {
        private final Ventas ventas;

        private final Directorio directorio;

        @PostMapping
        public ResponseEntity<Factura> createFactura(@RequestBody Factura factura) {
            Factura saved = ventas.storeFactura(factura);
            return ResponseEntity.ok(saved);
        }

        @GetMapping("/persona/{identificacion}")
        public ResponseEntity<List<FacturaDto>> getFacturasByPersona(@PathVariable String identificacion) {
            Persona persona = directorio.findPersonaByIdentificacion(identificacion);

            if (persona == null) {
                return ResponseEntity.notFound().build();
            }

            List<Factura> facturas = ventas.findFacturasByPersona(persona);

            List<FacturaDto> dtos = facturas.stream()
                    .map(FacturaMapper::toDTO)
                    .toList();

            return ResponseEntity.ok(dtos);
        }

    }
