package com.example.Test.services;

import com.example.Test.entities.Factura;
import com.example.Test.entities.Persona;
import com.example.Test.repositories.FacturaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class Ventas {
    private final FacturaRepository facturaRepository;

    public Factura storeFactura(Factura factura) {
        return facturaRepository.save(factura);
    }

    public List<Factura> findFacturasByPersona(Persona persona) {
        return facturaRepository.findByPersona(persona);
    }
}
