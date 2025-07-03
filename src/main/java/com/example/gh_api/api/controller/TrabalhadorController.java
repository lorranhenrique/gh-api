package com.example.gh_api.api.controller;

import com.example.gh_api.api.dto.TrabalhadorDTO;
import com.example.gh_api.exception.RegraNegocioException;
import com.example.gh_api.model.entity.Trabalhador;
import com.example.gh_api.service.TrabalhadorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/trabalhadores")
@RequiredArgsConstructor
@CrossOrigin

public class TrabalhadorController {

    private final TrabalhadorService service;

    @GetMapping()
    public ResponseEntity get(){
        List<Trabalhador> trabalhadores = service.getAllTrabalhadores();
        return ResponseEntity.ok(trabalhadores.stream().map(TrabalhadorDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable("id") Long id){
        Optional<Trabalhador> trabalhador = service.getTrabalhadorById(id);
        if(!trabalhador.isPresent()){
            return new ResponseEntity("Trabalhador não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(trabalhador.map(TrabalhadorDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody TrabalhadorDTO dto){
        try{
            Trabalhador trabalhador = convert(dto);
            trabalhador = service.save(trabalhador);
            return new ResponseEntity(trabalhador, HttpStatus.CREATED);
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody TrabalhadorDTO dto){
        if(!service.getTrabalhadorById(id).isPresent()){
            return new ResponseEntity("Trabalhador não encontrado", HttpStatus.NOT_FOUND);
        }
        try{
            Trabalhador trabalhador = convert(dto);
            trabalhador.setId(id);
            trabalhador = service.save(trabalhador);
            return ResponseEntity.ok(trabalhador);
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        Optional<Trabalhador> trabalhador = service.getTrabalhadorById(id);
        if(!trabalhador.isPresent()){
            return new ResponseEntity("Trabalhador não encontrado", HttpStatus.NOT_FOUND);
        }
        try{
            service.delete(trabalhador.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body("Não foi possível excluir o trabalhador: " + e.getMessage());
        }
    }

    public Trabalhador convert(TrabalhadorDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Trabalhador trabalhador = modelMapper.map(dto, Trabalhador.class);
        return trabalhador;
    }

}
