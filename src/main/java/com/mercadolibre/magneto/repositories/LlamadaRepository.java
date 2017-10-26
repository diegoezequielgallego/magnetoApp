package com.mercadolibre.magneto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mercadolibre.magneto.model.Llamada;

@Repository
public interface LlamadaRepository extends JpaRepository<Llamada, Long> {


}
