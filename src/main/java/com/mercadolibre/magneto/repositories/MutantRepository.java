package com.mercadolibre.magneto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mercadolibre.magneto.model.Mutant;

@Repository
public interface MutantRepository extends JpaRepository<Mutant, Long>{


}
