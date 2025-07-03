package com.example.gh_api.service;

import com.example.gh_api.exception.RegraNegocioException;
import com.example.gh_api.model.repository.CargoRepository;
import org.springframework.stereotype.Service;

import com.example.gh_api.model.entity.Cargo;

import jakarta.transaction.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CargoService {

    private CargoRepository repository;

    public CargoService(CargoRepository repository) {
        this.repository = repository;
    }

    public List<Cargo> getAllCargos() {
        return repository.findAll();
    }

    public Optional<Cargo> getCargoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Cargo save(Cargo cargo) {
        validate(cargo);
        return repository.save(cargo);
    }

    @Transactional
    public void delete(Cargo cargo) {
        Objects.requireNonNull(cargo.getId());
        repository.delete(cargo);
    }

    public void validate(Cargo cargo) {
        ArrayList<String> missingFields = new ArrayList<>();
        if (cargo.getNome() == null || cargo.getNome().trim().equals("")) {
            missingFields.add("nome");
        }

        if (cargo.getDescricao() == null || cargo.getDescricao().trim().equals("")) {
            missingFields.add("descrição");
        }

        if (cargo.getSalario() == null || cargo.getSalario() < 0) {
            missingFields.add("salario");
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
