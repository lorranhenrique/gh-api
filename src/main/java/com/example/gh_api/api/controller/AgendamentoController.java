package com.example.gh_api.api.controller;

import com.example.gh_api.api.dto.AgendamentoDTO;
import com.example.gh_api.exception.RegraNegocioException;
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

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody AgendamentoDTO dto){
        if(!service.getAgendamentoById(id).isPresent()){
            return new ResponseEntity("Agendamento não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Agendamento agendamento = convert(dto);
            agendamento.setId(id);
            agendamento = service.save(agendamento);
            return ResponseEntity.ok(AgendamentoDTO.create(agendamento));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        Optional<Agendamento> agendamento = service.getAgendamentoById(id);
        if(!agendamento.isPresent()){
            return new ResponseEntity("Agendamento não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.delete(agendamento.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body("Não foi possível excluir o agendamento. " + e.getMessage());
        }
    }

    public Agendamento convert(AgendamentoDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Agendamento agendamento = modelMapper.map(dto, Agendamento.class);
        return agendamento;
    }

}
