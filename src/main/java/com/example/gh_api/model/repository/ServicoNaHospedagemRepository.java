package com.example.gh_api.model.repository;

import com.example.gh_api.model.entity.ServicoNaHospedagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicoNaHospedagemRepository extends JpaRepository<ServicoNaHospedagem, Long> {
}