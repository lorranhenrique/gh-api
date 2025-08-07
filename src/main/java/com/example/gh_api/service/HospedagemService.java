package com.example.gh_api.service;

import com.example.gh_api.exception.RegraNegocioException;
import com.example.gh_api.model.entity.Hospedagem;
import com.example.gh_api.model.entity.Quarto;
import com.example.gh_api.model.entity.QuartoNaHospedagem;
import com.example.gh_api.model.repository.HospedagemRepository;
import org.springframework.stereotype.Service;

import jakarta.transaction.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class HospedagemService {

    private HospedagemRepository repository;

    public HospedagemService(HospedagemRepository repository) {
        this.repository = repository;
    }

    public List<Hospedagem> getAllHospedagem() {
        return repository.findAll();
    }

    public Optional<Hospedagem> getHospedagemById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Hospedagem save(Hospedagem hospedagem) {
        validate(hospedagem);
        hospedagem.getQuartoNaHospedagem().forEach(quartoNaHospedagem -> {
            Quarto quarto = quartoNaHospedagem.getQuarto();
            quarto.setSituacao("Reservado");
        });
        return repository.save(hospedagem);
    }

    @Transactional
    public void delete(Hospedagem hospedagem) {
        Objects.requireNonNull(hospedagem.getId());
        repository.delete(hospedagem);
    }

    public void validate(Hospedagem hospedagem) {
        ArrayList<String> missingFields = new ArrayList<>();

        if (hospedagem.getHotel() == null) {
            missingFields.add("hotel");
        }
        if (hospedagem.getHospede() == null) {
            missingFields.add("hospede");
        }
        if(hospedagem.getCheckIn() == null || hospedagem.getCheckIn().trim().equals("")) {
            missingFields.add("check-in");
        }
        if(hospedagem.getCheckOut() == null || hospedagem.getCheckOut().trim().equals("")) {
            missingFields.add("check-out");
        }
        if(hospedagem.getCriancas() == null || hospedagem.getCriancas() < 0) {
            missingFields.add("criancas");
        }
        if(hospedagem.getAdultos() == null || hospedagem.getAdultos() < 0) {
            missingFields.add("adultos");
        }

        if (hospedagem.getQuartoNaHospedagem() == null || hospedagem.getQuartoNaHospedagem().isEmpty()) {
            missingFields.add("quarto");
        } else {
            for (QuartoNaHospedagem quartoNaHospedagem : hospedagem.getQuartoNaHospedagem()) {
                Quarto quarto = quartoNaHospedagem.getQuarto();
                if (quarto == null) {
                    missingFields.add("quarto");
                }
            }
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime checkIn;
        LocalDateTime checkOut;
        try {
            checkIn = LocalDateTime.parse(hospedagem.getCheckIn(), formatter);
            checkOut = LocalDateTime.parse(hospedagem.getCheckOut(), formatter);
        } catch (Exception e) {
            throw new RegraNegocioException("Formato de data inválido.");
        }
        
        if (checkIn.isAfter(checkOut)) {
            throw new RegraNegocioException("A data de check-in deve ser anterior à data de check-out.");
        }

        for (QuartoNaHospedagem quartoNaHospedagem : hospedagem.getQuartoNaHospedagem()) {
            Quarto quarto = quartoNaHospedagem.getQuarto();
            if (quarto.getSituacao() != "Disponível") {
                throw new RegraNegocioException("O quarto " + quarto.getNumero() + " não está disponível.");
            }
        }
    }
}
