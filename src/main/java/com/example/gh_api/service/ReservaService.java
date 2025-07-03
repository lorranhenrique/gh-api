package com.example.gh_api.service;

import com.example.gh_api.exception.RegraNegocioException;
import com.example.gh_api.model.entity.Reserva;
import com.example.gh_api.model.repository.ReservaRepository;
import org.springframework.stereotype.Service;

import jakarta.transaction.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ReservaService {

    private ReservaRepository repository;

    public ReservaService(ReservaRepository repository) {
        this.repository = repository;
    }

    public List<Reserva> getAllReservas() {
        return repository.findAll();
    }

    public Optional<Reserva> getReservasById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Reserva save(Reserva reserva) {
        validate(reserva);
        return repository.save(reserva);
    }

    @Transactional
    public void delete(Reserva reserva) {
        Objects.requireNonNull(reserva.getId());
        repository.delete(reserva);
    }

    public void validate(Reserva reserva) {
        ArrayList<String> missingFields = new ArrayList<>();

        if (reserva.getHotel() == null) {
            missingFields.add("hotel");
        }
        if (reserva.getHospede() == null) {
            missingFields.add("hospede");
        }
        if (reserva.getTipoDeQuarto() == null) {
            missingFields.add("tipo de quarto");
        }
        if(reserva.getDataChegada() == null || reserva.getDataChegada().trim().equals("")) {
            missingFields.add("data de chegada");
        }
        if(reserva.getDataSaida() == null || reserva.getDataSaida().trim().equals("")) {
            missingFields.add("data de saida");
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
