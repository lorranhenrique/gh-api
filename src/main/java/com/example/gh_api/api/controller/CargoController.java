package com.example.gh_api.api.controller;

import com.example.gh_api.api.dto.CargoDTO;

import com.example.gh_api.model.entity.Cargo;
import com.example.gh_api.service.CargoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Cargo", description = "Gerenciador de cargos")

public class CargoController {

    private final CargoService service;


    @Operation(summary = "Busca cargos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cargos encontrados"),
            @ApiResponse(responseCode = "404", description = "Cargos não encontrados")
    })
    @GetMapping()
    public ResponseEntity get(){
        List<Cargo> cargos = service.getAllCargos();
        return ResponseEntity.ok(cargos.stream().map(CargoDTO::create).collect(Collectors.toList()));
    }

    @Operation(summary = "Busca cargo pelo id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cargo encontrado"),
            @ApiResponse(responseCode = "404", description = "Cargo não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable("id") Long id){
        Optional<Cargo> cargo = service.getCargoById(id);
        if(!cargo.isPresent()){
            return new ResponseEntity("Cargo não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(cargo.map(CargoDTO::create));
    }

    @Operation(summary = "Cria cargo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cargo criado"),
            @ApiResponse(responseCode = "404", description = "Falha ao criar cargo")
    })
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

    @Operation(summary = "Atualiza cargo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cargo atualizado"),
            @ApiResponse(responseCode = "404", description = "Falha ao atualizar cargo")
    })
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

    @Operation(summary = "Deleta cargo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cargo deletado"),
            @ApiResponse(responseCode = "404", description = "Falha ao deletar cargo")
    })
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
