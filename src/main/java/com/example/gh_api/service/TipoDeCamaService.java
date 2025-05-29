package com.example.gh_api.service;

import com.example.gh_api.exception.RegraNegocioException;
import com.example.gh_api.model.entity.TipoDeCama;
import com.example.gh_api.model.repository.TipoDeCamaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TipoDeCamaService {

    private TipoDeCamaRepository repository;

    public TipoDeCamaService(TipoDeCamaRepository repository) {
        this.repository = repository;
    }

    public List<TipoDeCama> getTipoCamas() {
        return repository.findAll();
    }

    public Optional<TipoDeCama> getTipoCamaById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public TipoDeCama save(TipoDeCama tipoDeCama){
        validate(tipoDeCama);
        return repository.save(tipoDeCama);
    }

    @Transactional
    public void delete(TipoDeCama tipoDeCama){
        Objects.requireNonNull(tipoDeCama.getId());
        repository.delete(tipoDeCama);
    }

    public void validate(TipoDeCama tipoDeCama){
        ArrayList<String> missingFields = new ArrayList<>();

        if (tipoDeCama.getTipo() == null || tipoDeCama.getTipo().trim().equals("")) {
            missingFields.add("tipo");
        }

        if (tipoDeCama.getQuantidadeAdultos() == null || tipoDeCama.getQuantidadeAdultos() <= 0) {
            missingFields.add("quantidade de adultos");
        }

        if (tipoDeCama.getQuantidadeCriancas() == null || tipoDeCama.getQuantidadeCriancas() <= 0) {
            missingFields.add("quantidade de crianças");
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