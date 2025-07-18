package com.example.gh_api.service;

import com.example.gh_api.exception.RegraNegocioException;
import com.example.gh_api.model.entity.Reserva;
import com.example.gh_api.model.repository.ReservaRepository;
import org.springframework.stereotype.Service;
import com.example.gh_api.model.entity.TipoDeQuartoNaReserva;
import com.example.gh_api.model.entity.TipoDeQuarto;
import java.util.Map;


import jakarta.transaction.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        if(reserva.getDataChegada() == null || reserva.getDataChegada().trim().equals("")) {
            missingFields.add("data de chegada");
        }
        if(reserva.getDataSaida() == null || reserva.getDataSaida().trim().equals("")) {
            missingFields.add("data de saida");
        }
        if (reserva.getTipoDeQuartoNaReserva() == null || reserva.getTipoDeQuartoNaReserva().isEmpty()) {
            missingFields.add("tipo de quarto na reserva");
        } else {
            for (TipoDeQuartoNaReserva tipoDeQuartoNaReserva : reserva.getTipoDeQuartoNaReserva()) {
                TipoDeQuarto tipoDeQuarto = tipoDeQuartoNaReserva.getTipoDeQuarto();
                Integer quantidade = tipoDeQuartoNaReserva.getQuantidade();

                if (tipoDeQuarto == null) {
                    missingFields.add("tipo de quarto");
                }
                if (quantidade == null || quantidade <= 0) {
                    missingFields.add("quantidade de quartos do tipo " + tipoDeQuarto.getTipo());
                }
            }
        }

        if (missingFields.size() > 0) {
            if (missingFields.size() == 1){
                throw new RegraNegocioException("Por favor, preencha o seguinte campo: " + missingFields.get(0) + ".");
            }
            else {
                throw new RegraNegocioException("Por favor, preencha os seguinte campos: " + String.join(", ", missingFields) + ".");
            }
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dataChegada;
        LocalDateTime dataSaida;
        try {
            dataChegada = LocalDateTime.parse(reserva.getDataChegada(), formatter);
            dataSaida = LocalDateTime.parse(reserva.getDataSaida(), formatter);
        } catch (Exception e) {
            throw new RegraNegocioException("Formato de data inválido.");
        }
        
        if (dataChegada.isAfter(dataSaida)) {
            throw new RegraNegocioException("A data de chegada deve ser anterior à data de saída.");
        }
        if (dataChegada.isBefore(LocalDateTime.now())) {
            throw new RegraNegocioException("A data de chegada não pode ser anterior à data atual.");
        }
    }
}
