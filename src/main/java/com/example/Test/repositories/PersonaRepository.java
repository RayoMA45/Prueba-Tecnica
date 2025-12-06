package com.example.Test.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.Test.entities.Persona;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {
    Optional<Persona> findByIdentificacion(String identificacion);
    
    @Transactional
    @Modifying
    @Query("DELETE FROM Persona p WHERE p.identificacion = :identificacion")
    void deleteByIdentificacion(String identificacion);

    Page<Persona> findAll(Pageable pageable);
}
