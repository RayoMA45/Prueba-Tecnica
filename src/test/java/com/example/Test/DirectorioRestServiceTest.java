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
        p1.setIdentificacion("123");

        Persona p2 = new Persona();
        p2.setId(2L);
        p2.setNombre("Ana");
        p2.setApellidoPaterno("Vazquez");
        p2.setIdentificacion("1234");

        when(directorio.findPersonas()).thenReturn(List.of(p1, p2));

        ResponseObject response = directorioRestService.getPersonas();

        assertTrue(response.isSuccess());
        assertEquals("Consulta de personas exitosa", response.getMessage());
        List<?> personas = (List<?>) ((java.util.Map<?, ?>) response.getResponse()).get("personas");
        assertEquals(2, personas.size());
    }

    @Test
    void testGetPersonaByIdentificacion() {
        Persona p1 = new Persona();
        p1.setId(1L);
        p1.setNombre("Marco");
        p1.setApellidoMaterno("Rayo");
        p1.setApellidoPaterno("Vazquez");
        p1.setIdentificacion("123");

        when(directorio.findPersonaByIdentificacion("123")).thenReturn(p1);

        ResponseObject response = directorioRestService.getPersona("123");

        assertTrue(response.isSuccess());
        assertEquals("Persona obtenida exitosamente", response.getMessage());
        PersonaDto personaDto = (PersonaDto) ((java.util.Map<?, ?>) response.getResponse()).get("persona");
        assertEquals("Marco", personaDto.getNombre());
    }

    @Test
    void testCreatePersona() {
        Persona p1 = new Persona();
        p1.setNombre("Marco");
        p1.setApellidoMaterno("Rayo");
        p1.setApellidoPaterno("Vazquez");
        p1.setIdentificacion("123");

        Persona saved = new Persona();
        saved.setId(5L);
        saved.setNombre(p1.getNombre());
        saved.setApellidoMaterno(p1.getApellidoMaterno());
        saved.setApellidoPaterno(p1.getApellidoPaterno());
        saved.setIdentificacion(p1.getIdentificacion());

        when(directorio.storePersona(p1)).thenReturn(saved);

        ResponseObject response = directorioRestService.createPersona(p1);

        assertTrue(response.isSuccess());
        assertEquals("Persona creada exitosamente", response.getMessage());
        PersonaDto personaDto = (PersonaDto) ((java.util.Map<?, ?>) response.getResponse()).get("persona");
        assertEquals(5L, personaDto.getId());
        assertEquals("Marco", personaDto.getNombre());
    }
}
