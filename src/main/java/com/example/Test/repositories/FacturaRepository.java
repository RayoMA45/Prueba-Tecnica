package com.example.Test.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Test.entities.Factura;
import com.example.Test.entities.Persona;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {
    List<Factura> findByPersona(Persona persona);

    void deleteByPersona(Persona persona);
}
