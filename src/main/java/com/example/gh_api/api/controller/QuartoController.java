package com.example.gh_api.api.controller;

import com.example.gh_api.api.dto.QuartoDTO;
import com.example.gh_api.exception.RegraNegocioException;
import com.example.gh_api.model.entity.Quarto;
import com.example.gh_api.service.QuartoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/quartos")
@RequiredArgsConstructor
@CrossOrigin

public class QuartoController {

    private final QuartoService service;

    @GetMapping()
    public ResponseEntity get(){
        List<Quarto> quartos = service.getAllQuartos();
        return ResponseEntity.ok(quartos.stream().map(QuartoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable("id") Long id){
        Optional<Quarto> quarto = service.getQuartoById(id);
        if(!quarto.isPresent()){
            return new ResponseEntity("Quarto não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(quarto.map(QuartoDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody QuartoDTO dto){
        try{
            Quarto quarto = convert(dto);
            quarto = service.save(quarto);
            return new ResponseEntity(quarto, HttpStatus.CREATED);
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody QuartoDTO dto){
        if(!service.getQuartoById(id).isPresent()){
            return new ResponseEntity("Quarto não encontrado", HttpStatus.NOT_FOUND);
        }
        try{
            Quarto quarto = convert(dto);
            quarto.setId(id);
            quarto = service.save(quarto);
            return ResponseEntity.ok(quarto);
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Quarto convert(QuartoDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Quarto quarto = modelMapper.map(dto, Quarto.class);
        return quarto;
    }
}
