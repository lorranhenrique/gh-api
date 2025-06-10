package com.example.gh_api.api.controller;

import com.example.gh_api.api.dto.HospedeDTO;

import com.example.gh_api.model.entity.Hospede;
import com.example.gh_api.service.HospedeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/hospedes")
@RequiredArgsConstructor
@CrossOrigin
public class HospedeController {

    private final HospedeService service;

    public Hospede convert(HospedeDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Hospede hospede = modelMapper.map(dto, Hospede.class);
        return hospede;
    }

    @GetMapping()
    public ResponseEntity get(){
        List<Hospede> hospede = service.getAllHospedes();
        return ResponseEntity.ok(hospede.stream().map(HospedeDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable("id") Long id){
        Optional<Hospede> hospede = service.getHospedeById(id);
        if(!hospede.isPresent()){
            return new ResponseEntity("Hospede não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(hospede.map(HospedeDTO::create));
    }


}
