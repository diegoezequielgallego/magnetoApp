package com.mercadolibre.magneto.repositories;

import com.mercadolibre.magneto.dto.CountDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mercadolibre.magneto.model.Mutant;

@Repository
public interface MutantRepository extends JpaRepository<Mutant, Long>{

    @Query(value = "select new com.mercadolibre.magneto.dto.CountDTO (COUNT(m)) FROM Mutant m WHERE isMutant = 1")
    CountDTO findTotales();

}
