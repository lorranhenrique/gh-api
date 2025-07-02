package com.example.gh_api.service;

import com.example.gh_api.exception.RegraNegocioException;
import com.example.gh_api.model.entity.CamaNoHotel;
import com.example.gh_api.model.repository.CamaNoHotelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CamaNoHotelService {
    private CamaNoHotelRepository repository;

    public CamaNoHotelService(CamaNoHotelRepository repository) {
        this.repository = repository;
    }

    public List<CamaNoHotel> getAllCamas(){ return repository.findAll(); }

    public Optional<CamaNoHotel> getCamaById(Long id){ return repository.findById(id); }

    @Transactional
    public CamaNoHotel save(CamaNoHotel cama){
        validate(cama);
        return repository.save(cama);
    }

    @Transactional
    public void delete(CamaNoHotel cama){
        Objects.requireNonNull(cama.getId());
        repository.delete(cama);
    }

    public void validate(CamaNoHotel cama){
        ArrayList<String> missingFields = new ArrayList<>();

        if (cama.getTipoDeCama() == null || cama.getTipoDeCama().getTipo().equals("")){
            missingFields.add("tipoDeCama");
        }

        if (cama.getQuantidade() == null || cama.getQuantidade() < 0){
            missingFields.add("quantidade");
        }

        if (cama.getHotel() == null || cama.getHotel().getNome().trim().equals("")) {
            missingFields.add("hotel");
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
