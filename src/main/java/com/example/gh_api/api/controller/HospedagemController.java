package com.example.gh_api.api.controller;

import com.example.gh_api.api.dto.HospedagemDTO;

import com.example.gh_api.model.entity.Hospedagem;
import com.example.gh_api.service.HospedagemService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.gh_api.exception.RegraNegocioException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/hospedagens")
@RequiredArgsConstructor
@CrossOrigin
public class HospedagemController {

    private final HospedagemService service;

    @GetMapping()
    public ResponseEntity get(){
        List<Hospedagem> hospedagens = service.getAllHospedagem();
        return ResponseEntity.ok(hospedagens.stream().map(HospedagemDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable("id") Long id){
        Optional<Hospedagem> hospedagens = service.getHospedagemById(id);
        if(!hospedagens.isPresent()){
            return new ResponseEntity("Hospedagem não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(hospedagens.map(HospedagemDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody HospedagemDTO dto){
        try {
            Hospedagem hospedagem = convert(dto);
            hospedagem = service.save(hospedagem);
            return new ResponseEntity(HospedagemDTO.create(hospedagem), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody HospedagemDTO dto){
        if(!service.getHospedagemById(id).isPresent()){
            return new ResponseEntity("Hospedagem não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Hospedagem hospedagem = convert(dto);
            hospedagem.setId(id);
            hospedagem = service.save(hospedagem);
            return ResponseEntity.ok(HospedagemDTO.create(hospedagem));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Hospedagem> hospedagem = service.getHospedagemById(id);
        if (!hospedagem.isPresent()) {
            return new ResponseEntity("Hóspedagem não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.delete(hospedagem.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body("Não foi possível excluir a hospedagem. " + e.getMessage());
        }
    }

    public Hospedagem convert(HospedagemDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Hospedagem hospedagem = modelMapper.map(dto, Hospedagem.class);
        return hospedagem;
    }
}
