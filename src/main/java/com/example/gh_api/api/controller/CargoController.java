package com.example.gh_api.api.controller;

import com.example.gh_api.api.dto.CargoDTO;

import com.example.gh_api.model.entity.Cargo;
import com.example.gh_api.service.CargoService;
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
@RequestMapping("/api/v1/cargos")
@RequiredArgsConstructor
@CrossOrigin

public class CargoController {

    private final CargoService service;


    @GetMapping()
    public ResponseEntity get(){
        List<Cargo> cargos = service.getAllCargos();
        return ResponseEntity.ok(cargos.stream().map(CargoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable("id") Long id){
        Optional<Cargo> cargo = service.getCargoById(id);
        if(!cargo.isPresent()){
            return new ResponseEntity("Cargo não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(cargo.map(CargoDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody CargoDTO dto){
        try {
            Cargo cargo = convert(dto);
            cargo = service.save(cargo);
            return new ResponseEntity(CargoDTO.create(cargo), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody CargoDTO dto){
        if(!service.getCargoById(id).isPresent()){
            return new ResponseEntity("Cargo não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Cargo cargo = convert(dto);
            cargo.setId(id);
            cargo = service.save(cargo);
            return ResponseEntity.ok(CargoDTO.create(cargo));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Cargo> cargo = service.getCargoById(id);
        if (!cargo.isPresent()) {
            return new ResponseEntity("Cargo não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.delete(cargo.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body("Não foi possível excluir o cargo. " + e.getMessage());
        }
    }

    public Cargo convert(CargoDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Cargo cargo = modelMapper.map(dto, Cargo.class);
        return cargo;
    }

}
