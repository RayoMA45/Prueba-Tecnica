package com.example.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.Test.controllers.DirectorioRestService;
import com.example.Test.dto.PersonaDto;
import com.example.Test.entities.Persona;
import com.example.Test.exceptions.ResponseObject;
import com.example.Test.services.Directorio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;


public class DirectorioRestServiceTest {

    @Mock
    private Directorio directorio;

    @InjectMocks
    private DirectorioRestService directorioRestService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPersonas() {
        Persona p1 = new Persona();
        p1.setId(1L);
        p1.setNombre("Marco");
        p1.setApellidoMaterno("Rayo");
        p1.setApellidoPaterno("Vazquez");

        Persona p2 = new Persona();
        p2.setId(2L);
        p2.setNombre("Ana");
        p1.setApellidoPaterno("Vazquez");

        when(directorio.findPersonas()).thenReturn(List.of(p1, p2));

        ResponseObject response = directorioRestService.getPersonas();

        assertTrue(response.isSuccess());
        assertEquals("Consulta de personas exitosa", response.getMessage());
        List<?> personas = (List<?>) ((java.util.Map<?, ?>) response.getResponse()).get("personas");
        assertEquals(2, personas.size());
    }

    @Test
    void testGetPersonaByIdentificacion() {
        Persona p = new Persona();
        p.setId(1L);
        p.setNombre("Marco");
        p.setIdentificacion("123");

        when(directorio.findPersonaByIdentificacion("123")).thenReturn(p);

        ResponseObject response = directorioRestService.getPersona("123");

        assertTrue(response.isSuccess());
        assertEquals("Persona obtenida exitosamente", response.getMessage());
        PersonaDto personaDto = (PersonaDto) ((java.util.Map<?, ?>) response.getResponse()).get("persona");
        assertEquals("Marco", personaDto.getNombre());
    }

    @Test
    void testCreatePersona() {
        Persona p = new Persona();
        p.setNombre("Laura");

        Persona saved = new Persona();
        saved.setId(5L);
        saved.setNombre("Laura");

        when(directorio.storePersona(p)).thenReturn(saved);

        ResponseObject response = directorioRestService.createPersona(p);

        assertTrue(response.isSuccess());
        assertEquals("Persona creada exitosamente", response.getMessage());
        PersonaDto personaDto = (PersonaDto) ((java.util.Map<?, ?>) response.getResponse()).get("persona");
        assertEquals(5L, personaDto.getId());
        assertEquals("Laura", personaDto.getNombre());
    }
}
