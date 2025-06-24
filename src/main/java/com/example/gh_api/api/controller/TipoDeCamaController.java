package com.example.gh_api.api.controller;

import com.example.gh_api.api.dto.TipoDeCamaDTO;
import com.example.gh_api.exception.RegraNegocioException;
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

    @PostMapping
    public ResponseEntity post(@RequestBody TipoDeCamaDTO dto){
        try {
            TipoDeCama tipoDeCama = convert(dto);
            tipoDeCama = service.save(tipoDeCama);
            return new ResponseEntity(TipoDeCamaDTO.create(tipoDeCama), HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody TipoDeCamaDTO dto){
        if(!service.getTipoCamaById(id).isPresent()){
            return new ResponseEntity("Tipo de cama não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            TipoDeCama tipoDeCama = convert(dto);
            tipoDeCama.setId(id);
            tipoDeCama = service.save(tipoDeCama);
            return ResponseEntity.ok(TipoDeCamaDTO.create(tipoDeCama));
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        Optional<TipoDeCama> tipoDeCama = service.getTipoCamaById(id);
        if(!tipoDeCama.isPresent()){
            return new ResponseEntity("Tipo de cama não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.delete(tipoDeCama.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body("Não foi possível excluir o tipo de cama: " + e.getMessage());
        }
    }

    public TipoDeCama convert(TipoDeCamaDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        TipoDeCama tipoDeCama = modelMapper.map(dto, TipoDeCama.class);
        return tipoDeCama;
    }
}
