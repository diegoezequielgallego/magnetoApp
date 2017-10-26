package com.mercadolibre.magneto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mercadolibre.magneto.model.Personal;

@Repository
public interface PersonalRepository extends JpaRepository<Personal, Long>{


}
