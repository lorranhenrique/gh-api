package com.example.gh_api.service;

import com.example.gh_api.api.dto.AgendamentoDTO;
import com.example.gh_api.exception.RegraNegocioException;
import com.example.gh_api.model.entity.Agendamento;
import com.example.gh_api.model.entity.Hospedagem;
import com.example.gh_api.model.entity.Servico;
import com.example.gh_api.model.entity.Trabalhador;
import com.example.gh_api.model.repository.AgendamentoRepository;
import com.example.gh_api.model.repository.HospedagemRepository;
import com.example.gh_api.model.repository.ServicoRepository;
import com.example.gh_api.model.repository.TrabalhadorRepository;

import org.springframework.stereotype.Service;

import jakarta.transaction.*;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AgendamentoService {

    private final AgendamentoRepository repository;
    private final HospedagemRepository hospedagemRepository;
    private final TrabalhadorRepository trabalhadorRepository;
    private final ServicoRepository servicoRepository;

    public AgendamentoService(AgendamentoRepository repository,
                             HospedagemRepository hospedagemRepository,
                             TrabalhadorRepository trabalhadorRepository,
                             ServicoRepository servicoRepository) {
        this.repository = repository;
        this.hospedagemRepository = hospedagemRepository;
        this.trabalhadorRepository = trabalhadorRepository;
        this.servicoRepository = servicoRepository;
    }

    public List<Agendamento> getAllAgendamento() {
        return repository.findAll();
    }

    public Optional<Agendamento> getAgendamentoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Agendamento save(AgendamentoDTO dto) {
        Hospedagem hospedagem = hospedagemRepository.findById(dto.getIdHospedagem())
                .orElseThrow(() -> new RegraNegocioException("Hospedagem não encontrada com o ID: " + dto.getIdHospedagem()));
        Trabalhador trabalhador = trabalhadorRepository.findById(dto.getIdTrabalhador())
                .orElseThrow(() -> new RegraNegocioException("Trabalhador não encontrado com o ID: " + dto.getIdTrabalhador()));

        Servico servico = servicoRepository.findById(dto.getIdServico())
                .orElseThrow(() -> new RegraNegocioException("Serviço não encontrado com o ID: " + dto.getIdServico()));
        
        Agendamento agendamento = new Agendamento();
        agendamento.setHospedagem(hospedagem);
        agendamento.setTrabalhador(trabalhador);
        agendamento.setServico(servico);
        agendamento.setHorarioInicio(dto.getHorarioInicio());
        agendamento.setData(dto.getData());
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

        ZonedDateTime data;

        try {
            data = ZonedDateTime.parse(agendamento.getData());
        } catch (Exception e) {
            throw new RegraNegocioException("Data inválida. Por favor, use o formato dd/MM/yyyy'T'HH:mm.");
        }
        if (data.isBefore(ZonedDateTime.now())) {
            throw new RegraNegocioException("A data do agendamento não pode ser anterior à data atual.");
        }

    }
}
