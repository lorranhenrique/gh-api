package com.example.gh_api.api.dto.controller;

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

    public Servico convert(ServicoDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Servico servico = modelMapper.map(dto, Servico.class);
        return servico;
    }
}
