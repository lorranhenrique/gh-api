package com.example.gh_api.service;

import com.example.gh_api.exception.RegraNegocioException;
import com.example.gh_api.model.entity.Hospedagem;
import com.example.gh_api.model.repository.HospedagemRepository;
import org.springframework.stereotype.Service;

import jakarta.transaction.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
        if (hospedagem.getCamasExtrasNaReserva() == null) {
            missingFields.add("camas extras");
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
        if(hospedagem.getQuantidadeDeQuartos() == null || hospedagem.getQuantidadeDeQuartos() < 0) {
            missingFields.add("quantidade de quartos");
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
