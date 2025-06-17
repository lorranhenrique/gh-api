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

    @PostMapping
    public ResponseEntity post(@RequestBody HospedeDTO dto){
        try {
            Hospede hospede = convert(dto);
            hospede = service.save(hospede);
            return new ResponseEntity(HospedeDTO.create(hospede), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody HospedeDTO dto){
        if(!service.getHospedeById(id).isPresent()){
            return new ResponseEntity("Hospede não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Hospede hospede = convert(dto);
            hospede.setId(id);
            hospede = service.save(hospede);
            return ResponseEntity.ok(HospedeDTO.create(hospede));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Hospede convert(HospedeDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Hospede hospede = modelMapper.map(dto, Hospede.class);
        return hospede;
    }
}
