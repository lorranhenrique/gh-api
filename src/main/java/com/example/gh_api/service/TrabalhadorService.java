package com.example.gh_api.service;

import com.example.gh_api.exception.RegraNegocioException;
import com.example.gh_api.model.entity.Trabalhador;
import com.example.gh_api.model.repository.TrabalhadorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TrabalhadorService{
    private TrabalhadorRepository repository;

    public TrabalhadorService(TrabalhadorRepository repository) { this.repository = repository; }

    public List<Trabalhador> getAllTrabalhadores() { return repository.findAll(); }

    public Optional<Trabalhador> getTrabalhadorById(Long id) { return repository.findById(id); }

    @Transactional
    public Trabalhador save(Trabalhador trabalhador){
        validate(trabalhador);
        return repository.save(trabalhador);
    }

    @Transactional
    public void delete(Trabalhador trabalhador){
        Objects.requireNonNull(trabalhador.getId());
        repository.delete(trabalhador);
    }

    public void validate(Trabalhador trabalhador){
        ArrayList<String> missingFields = new ArrayList<>();

        if (trabalhador.getNome() == null || trabalhador.getNome().trim().equals("")) {
            missingFields.add("nome");
        }

        if (trabalhador.getGenero() == null || trabalhador.getGenero().trim().equals("")) {
            missingFields.add("gênero");
        }

        if (trabalhador.getTelefone() == null || trabalhador.getTelefone().trim().equals("")) {
            missingFields.add("telefone");
        }

        if (trabalhador.getEmail() == null || trabalhador.getEmail().trim().equals("")) {
            missingFields.add("e-mail");
        }

        if (trabalhador.getCpf() == null || trabalhador.getCpf().trim().equals("")) {
            missingFields.add("CPF");
        }

        if (trabalhador.getEstado() == null || trabalhador.getEstado().trim().equals("")) {
            missingFields.add("estado");
        }

        if (trabalhador.getCidade() == null || trabalhador.getCidade().trim().equals("")) {
            missingFields.add("cidade");
        }

        if (trabalhador.getCep() == null || trabalhador.getCep().trim().equals("")) {
            missingFields.add("cep");
        }

        if (trabalhador.getBairro() == null || trabalhador.getBairro().trim().equals("")) {
            missingFields.add("bairro");
        }

        if (trabalhador.getLogradouro() == null || trabalhador.getLogradouro().trim().equals("")) {
            missingFields.add("logradouro");
        }

        if (trabalhador.getNumeroMoradia() == null || trabalhador.getNumeroMoradia().trim().equals("")) {
            missingFields.add("número");
        }

        if (trabalhador.getDataNascimento() == null || trabalhador.getDataNascimento().trim().equals("")) {
            missingFields.add("data de nascimento");
        }

        if (trabalhador.getHotel() == null) {
            missingFields.add("hotel");
        }

        if (trabalhador.getCargo() == null || trabalhador.getCargo().getNome().trim().equals("")) {
            missingFields.add("cargo");
        }

        if (missingFields.size() > 0) {
            if (missingFields.size() == 1) {
                throw new RegraNegocioException("Por favor, preencha o seguinte campo: " + missingFields.get(0) + ".");
            } else {
                throw new RegraNegocioException("Por favor, preencha os seguinte campos: " + String.join(", ", missingFields) + ".");
            }
        }
    }
}
