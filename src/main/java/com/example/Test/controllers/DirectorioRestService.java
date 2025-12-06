package com.example.Test.controllers;

import com.example.Test.entities.Persona;
import com.example.Test.services.Directorio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequiredArgsConstructor
@RequestMapping("/personas")
public class DirectorioRestService {
    private final Directorio directorio;

    @GetMapping
    public ResponseEntity<List<Persona>> getPersonas() {
        return ResponseEntity.ok(directorio.findPersonas());
    }

    @GetMapping("/{identificacion}")
    public ResponseEntity<Persona> getPersona(@PathVariable String identificacion) {
        Persona persona = directorio.findPersonaByIdentificacion(identificacion);

        return persona != null ? ResponseEntity.ok(persona) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Persona> createPersona(@RequestBody Persona persona) {
        Persona saved = directorio.storePersona(persona);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{identificacion}")
    public ResponseEntity<Void> deletePersona(@PathVariable String identificacion) {
        directorio.deletePersonaByIdentificacion(identificacion);
        System.out.println(identificacion);
        return ResponseEntity.noContent().build();        
    }
}
