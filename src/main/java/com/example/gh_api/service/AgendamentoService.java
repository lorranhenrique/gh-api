package com.example.gh_api.service;

import com.example.gh_api.exception.RegraNegocioException;
import com.example.gh_api.model.entity.Agendamento;
import com.example.gh_api.model.repository.AgendamentoRepository;
import org.springframework.stereotype.Service;

import jakarta.transaction.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AgendamentoService {

    private AgendamentoRepository repository;

    public AgendamentoService(AgendamentoRepository repository) {
        this.repository = repository;
    }

    public List<Agendamento> getAllAgendamento() {
        return repository.findAll();
    }

    public Optional<Agendamento> getAgendamentoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Agendamento save(Agendamento agendamento) {
        validate(agendamento);
        return repository.save(agendamento);
    }

    @Transactional
    public void delete(Agendamento agendamento) {
        Objects.requireNonNull(agendamento.getId());
        repository.delete(agendamento);
    }

    public void validate(Agendamento agendamento) {
        ArrayList<String> missingFields = new ArrayList<>();

        if(agendamento.getHospedagem() == null) {
            missingFields.add("hospedagem");
        }
        /*if(agendamento.getAgenda() == null) {
            missingFields.add("agenda");
        }*/
        if(agendamento.getServico() == null) {
            missingFields.add("serviço");
        }
        if(agendamento.getHorarioInicio() == null || agendamento.getHorarioInicio().trim().equals("")) {
            missingFields.add("horário de início");
        }
        if(agendamento.getData() == null || agendamento.getData().trim().equals("")) {
            missingFields.add("data");
        }
        if(agendamento.getTrabalhador() == null) {
            missingFields.add("trabalhador");
        }

        if (missingFields.size() > 0) {
            if (missingFields.size() == 1){
                throw new RegraNegocioException("Por favor, preencha o seguinte campo: " + missingFields.get(0) + ".");
            }
            else {
                throw new RegraNegocioException("Por favor, preencha os seguinte campos: " + String.join(", ", missingFields) + ".");
            }
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy'T'HH:mm");
        LocalDateTime data;
        try {
            data = LocalDateTime.parse(agendamento.getData(), formatter);
        } catch (Exception e) {
            throw new RegraNegocioException("Data inválida. Por favor, use o formato dd/MM/yyyy'T'HH:mm.");
        }
        if (data.isBefore(LocalDateTime.now())) {
            throw new RegraNegocioException("A data do agendamento não pode ser anterior à data atual.");
        }

    }
}
