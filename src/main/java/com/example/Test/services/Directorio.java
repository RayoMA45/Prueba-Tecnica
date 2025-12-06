package com.example.Test.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Test.entities.Persona;
import com.example.Test.repositories.FacturaRepository;
import com.example.Test.repositories.PersonaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Directorio {
    private final PersonaRepository personaRepository;

    private final FacturaRepository facturaRepository;

    public Persona findPersonaByIdentificacion(String identificacion) {
        return personaRepository.findByIdentificacion(identificacion).orElse(null);
    }

    public List<Persona> findPersonas() {
        return personaRepository.findAll();
    }

    public Persona storePersona(Persona persona) {
        return personaRepository.save(persona);
    }

    public void deletePersonaByIdentificacion(String identificacion) {
        Persona persona = personaRepository.findByIdentificacion(identificacion).orElse(null);
        if (persona != null) {
            facturaRepository.deleteByPersona(persona);
            personaRepository.deleteByIdentificacion(identificacion);
        }
    }

    public Page<Persona> findPersonasPage(Pageable pageable) {
    return personaRepository.findAll(pageable);
}
}