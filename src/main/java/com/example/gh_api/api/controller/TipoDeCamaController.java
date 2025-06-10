package com.example.gh_api.api.controller;

import com.example.gh_api.api.dto.TipoDeCamaDTO;

import com.example.gh_api.model.entity.TipoDeCama;
import com.example.gh_api.service.TipoDeCamaService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/tipoDeCama")
@RequiredArgsConstructor
@CrossOrigin
public class TipoDeCamaController {

    private final TipoDeCamaService service;

    public TipoDeCama convert(TipoDeCamaDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        TipoDeCama tipoDeCama = modelMapper.map(dto, TipoDeCama.class);
        return tipoDeCama;
    }

    @GetMapping()
    public ResponseEntity get(){
        List<TipoDeCama> tipoDeCamas = service.getTipoCamas();
        return ResponseEntity.ok(tipoDeCamas.stream().map(TipoDeCamaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable("id") Long id){
        Optional<TipoDeCama> tipoDeCama = service.getTipoCamaById(id);
        if(!tipoDeCama.isPresent()){
            return new ResponseEntity("Tipo de cama  não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(tipoDeCama.map(TipoDeCamaDTO::create));
    }


}
