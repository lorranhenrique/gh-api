package com.example.gh_api.service;

import com.example.gh_api.exception.RegraNegocioException;
import com.example.gh_api.model.entity.Agenda;
import com.example.gh_api.model.repository.AgendaRepository;
import org.springframework.stereotype.Service;

import jakarta.transaction.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AgendaService {

    private AgendaRepository repository;

    public AgendaService(AgendaRepository repository) {
        this.repository = repository;
    }

    public List<Agenda> getAllAgendas() {
        return repository.findAll();
    }

    public Optional<Agenda> getAgendaById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Agenda save(Agenda agenda) {
        validate(agenda);
        return repository.save(agenda);
    }

    @Transactional
    public void delete(Agenda agenda) {
        Objects.requireNonNull(agenda.getId());
        repository.delete(agenda);
    }

    public void validate(Agenda agenda) {
        ArrayList<String> missingFields = new ArrayList<>();

        if (agenda.getHorarioInicio() == null || agenda.getHorarioInicio().trim().equals("")) {
            missingFields.add("horario de inicio");
        }
        if(agenda.getHorarioFim() == null || agenda.getHorarioFim().trim().equals("")) {
            missingFields.add("horario de fim");
        }
        if(agenda.getDias() == null || agenda.getDias().trim().equals("")) {
            missingFields.add("dias");
        }
        if(agenda.getServico() == null){
            missingFields.add("servico");
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
