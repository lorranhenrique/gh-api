package com.example.gh_api.api.controller;

import com.example.gh_api.api.dto.HospedeDTO;

import com.example.gh_api.model.entity.Hospede;
import com.example.gh_api.service.HospedeService;
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
@RequestMapping("/api/v1/hospedes")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name = "Hospede", description = "Gerenciador de hóspedes")
public class HospedeController {

    private final HospedeService service;


    @Operation(summary = "Busca hóspedes")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Hóspedes encontrados"),
            @ApiResponse(responseCode = "404", description = "Hóspedes não encontrados")
    })
    @GetMapping()
    public ResponseEntity get(){
        List<Hospede> hospede = service.getAllHospedes();
        return ResponseEntity.ok(hospede.stream().map(HospedeDTO::create).collect(Collectors.toList()));
    }

    @Operation(summary = "Busca hóspede pelo id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Hóspede encontrado"),
            @ApiResponse(responseCode = "404", description = "Hóspede não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable("id") Long id){
        Optional<Hospede> hospede = service.getHospedeById(id);
        if(!hospede.isPresent()){
            return new ResponseEntity("Hospede não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(hospede.map(HospedeDTO::create));
    }

    @Operation(summary = "Cria hóspede")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Hóspede criado"),
            @ApiResponse(responseCode = "404", description = "Falha ao criar hóspede")
    })
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

    @Operation(summary = "Atualiza hóspede")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Hóspede atualizado"),
            @ApiResponse(responseCode = "404", description = "Falha ao atualizar hóspede")
    })
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

    @Operation(summary = "Deleta hóspede")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Hóspede deletado"),
            @ApiResponse(responseCode = "404", description = "Falha ao deletar hóspede")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Hospede> hospede = service.getHospedeById(id);
        if (!hospede.isPresent()) {
            return new ResponseEntity("Hóspede não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.delete(hospede.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body("Não foi possível excluir o hóspede. " + e.getMessage());
        }
    }

    public Hospede convert(HospedeDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Hospede hospede = modelMapper.map(dto, Hospede.class);
        return hospede;
    }
}
