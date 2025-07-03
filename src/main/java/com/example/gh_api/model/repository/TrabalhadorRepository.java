package com.example.gh_api.model.repository;

import com.example.gh_api.model.entity.Trabalhador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrabalhadorRepository extends JpaRepository<Trabalhador, Long> {

}
