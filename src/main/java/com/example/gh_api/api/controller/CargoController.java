package com.example.gh_api.api.controller;

import com.example.gh_api.api.dto.CargoDTO;

import com.example.gh_api.exception.RegraNegocioException;
import com.example.gh_api.model.entity.Cargo;
import com.example.gh_api.service.CargoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/cargos")
@RequiredArgsConstructor
@CrossOrigin

public class CargoController {

    private final CargoService service;

    public Cargo convert(CargoDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Cargo cargo = modelMapper.map(dto, Cargo.class);
        return cargo;
    }
}
