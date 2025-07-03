package com.example.gh_api.model.repository;

import com.example.gh_api.model.entity.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendaRepository extends JpaRepository<Agenda, Long> {

}