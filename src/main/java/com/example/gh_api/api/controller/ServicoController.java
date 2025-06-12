package com.example.gh_api.api.controller;

import com.example.gh_api.api.dto.ServicoDTO;

import com.example.gh_api.exception.RegraNegocioException;
import com.example.gh_api.model.entity.Servico;
import com.example.gh_api.service.ServicoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/servicos")
@RequiredArgsConstructor
@CrossOrigin

public class ServicoController {

    private final ServicoService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Servico> servicos = service.getAllServicos();
        return ResponseEntity.ok(servicos.stream().map(ServicoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable("id") Long id) {
        Optional<Servico> servico = service.getServicoById(id);
        if(!servico.isPresent()){
            return new ResponseEntity("Serviço não encontrado ", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(servico.map(ServicoDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody ServicoDTO dto) {
        try {
            Servico servico = convert(dto);
            servico = service.save(servico);
            return new ResponseEntity(ServicoDTO.create(servico), HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Servico convert(ServicoDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Servico servico = modelMapper.map(dto, Servico.class);
        return servico;
    }

}
