package com.example.gh_api.service;

import com.example.gh_api.model.entity.TipoDeCama;
import com.example.gh_api.model.repository.TipoDeCamaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    }

}
