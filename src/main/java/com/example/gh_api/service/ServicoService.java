package com.example.gh_api.service;

import com.example.gh_api.exception.RegraNegocioException;
import com.example.gh_api.model.repository.ServicoRepository;
import org.springframework.stereotype.Service;
import com.example.gh_api.model.entity.Servico;

import jakarta.transaction.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ServicoService {

    private ServicoRepository repository;

    public ServicoService(ServicoRepository repository) {
        this.repository = repository;
    }

    public List<Servico> getAllServicos() {
        return repository.findAll();
    }

    public Optional<Servico> getServicoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Servico save(Servico servico) {
        validate(servico);
        return repository.save(servico);
    }

    @Transactional
    public void delete(Servico servico) {
        Objects.requireNonNull(servico.getId());
        repository.delete(servico);
    }

    public void validate(Servico servico) {
        ArrayList<String> missingFields = new ArrayList<>();

        if (servico.getNome() == null || servico.getNome().trim().equals("")) {
            missingFields.add("nome");
        }

        if (servico.getDescricao() == null || servico.getDescricao().trim().equals("")) {
            missingFields.add("descrição");
        }

        if (servico.getPreco() == null || servico.getPreco() <= 0) {
            missingFields.add("preço");
        }

        if (servico.getMinutosPorAgendamento() == null || servico.getMinutosPorAgendamento() <= 0) {
            missingFields.add("minutos por agendamento");
        }

        if (missingFields.size() > 0) {
            if (missingFields.size() == 1){
                throw new RegraNegocioException("Por favor, preencha o seguinte campo: " + missingFields.get(0) + ".");
            }
            else {
                throw new RegraNegocioException("Por favor, preencha os seguinte campos: " + String.join(", ", missingFields) + ".");
            }
        }
    }
}
