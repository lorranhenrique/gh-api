package com.example.gh_api.api.controller;

import com.example.gh_api.api.dto.AgendamentoDTO;

import com.example.gh_api.model.entity.Agendamento;
import com.example.gh_api.service.AgendamentoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/agendamentos")
@RequiredArgsConstructor
@CrossOrigin

public class AgendamentoController {

    private final AgendamentoService service;


    @GetMapping()
    public ResponseEntity get(){
        List<Agendamento> agendamentos = service.getAllAgendamento();
        return ResponseEntity.ok(agendamentos.stream().map(AgendamentoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable("id") Long id){
        Optional<Agendamento> agendamento = service.getAgendamentoById(id);
        if(!agendamento.isPresent()){
            return new ResponseEntity("Agendamento não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(agendamento.map(AgendamentoDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody AgendamentoDTO dto){
        try {
            Agendamento agendamento = convert(dto);
            agendamento = service.save(agendamento);
            return new ResponseEntity(AgendamentoDTO.create(agendamento), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Agendamento convert(AgendamentoDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Agendamento agendamento = modelMapper.map(dto, Agendamento.class);
        return agendamento;
    }

}
