package com.example.gh_api.service;

import com.example.gh_api.exception.RegraNegocioException;
import com.example.gh_api.model.entity.Hospede;
import com.example.gh_api.model.repository.HospedeRepository;
import org.springframework.stereotype.Service;

import jakarta.transaction.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class HospedeService {

    private HospedeRepository repository;

    public HospedeService(HospedeRepository repository) {
        this.repository = repository;
    }

    public List<Hospede> getAllHospedes() {
        return repository.findAll();
    }

    public Optional<Hospede> getHospedeById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Hospede save(Hospede hospede) {
        validate(hospede);
        return repository.save(hospede);
    }

    @Transactional
    public void delete(Hospede hospede) {
        Objects.requireNonNull(hospede.getId());
        repository.delete(hospede);
    }

    public void validate(Hospede hospede) {
        ArrayList<String> missingFields = new ArrayList<>();

        if (hospede.getNome() == null || hospede.getNome().trim().equals("")) {
            missingFields.add("nome");
        }

        if (hospede.getGenero() == null || hospede.getGenero().trim().equals("")) {
            missingFields.add("gênero");
        }

        if (hospede.getTelefone() == null || hospede.getTelefone().trim().equals("")) {
            missingFields.add("telefone");
        }

        if (hospede.getEmail() == null || hospede.getEmail().trim().equals("")) {
            missingFields.add("e-mail");
        }

        if (hospede.getCpf() == null || hospede.getCpf().trim().equals("")) {
            missingFields.add("CPF");
        }

        if (hospede.getEstado() == null || hospede.getEstado().trim().equals("")) {
            missingFields.add("estado");
        }

        if (hospede.getCidade() == null || hospede.getCidade().trim().equals("")) {
            missingFields.add("cidade");
        }

        if (hospede.getCep() == null || hospede.getCep().trim().equals("")) {
            missingFields.add("cep");
        }

        if (hospede.getBairro() == null || hospede.getBairro().trim().equals("")) {
            missingFields.add("bairro");
        }

        if (hospede.getLogradouro() == null || hospede.getLogradouro().trim().equals("")) {
            missingFields.add("logradouro");
        }

        if (hospede.getNumeroMoradia() == null || hospede.getNumeroMoradia().trim().equals("")) {
            missingFields.add("número");
        }

        if (hospede.getDataNascimento() == null || hospede.getDataNascimento().trim().equals("")) {
            missingFields.add("data de nascimento");
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
