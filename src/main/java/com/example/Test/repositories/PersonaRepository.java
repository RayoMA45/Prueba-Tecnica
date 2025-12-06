package com.example.Test.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.Test.entities.Persona;

import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {
    Optional<Persona> findByIdentificacion(String identificacion);
    
    @Transactional
    @Modifying
    @Query("DELETE FROM Persona p WHERE p.identificacion = :identificacion")
    void deleteByIdentificacion(String identificacion);
}
