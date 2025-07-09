package com.example.gh_api.api.controller;

import com.example.gh_api.api.dto.ServicoDTO;

import com.example.gh_api.exception.RegraNegocioException;
import com.example.gh_api.model.entity.Servico;
import com.example.gh_api.service.ServicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/servicos")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name = "Serviço", description = "Gerenciador de serviços")

public class ServicoController {

    private final ServicoService service;

    @Operation(summary = "Busca serviços")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Serviços encontrados"),
            @ApiResponse(responseCode = "404", description = "Serviços não encontrados")
    })
    @GetMapping()
    public ResponseEntity get() {
        List<Servico> servicos = service.getAllServicos();
        return ResponseEntity.ok(servicos.stream().map(ServicoDTO::create).collect(Collectors.toList()));
    }

    @Operation(summary = "Busca serviço pelo id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Serviço encontrado"),
            @ApiResponse(responseCode = "404", description = "Serviço não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable("id") Long id) {
        Optional<Servico> servico = service.getServicoById(id);
        if(!servico.isPresent()){
            return new ResponseEntity("Serviço não encontrado ", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(servico.map(ServicoDTO::create));
    }

    @Operation(summary = "Cria serviço")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Serviço criado"),
            @ApiResponse(responseCode = "404", description = "Falha ao criar serviço")
    })
    @PostMapping
    public ResponseEntity post(@RequestBody ServicoDTO dto) {
        try {
            Servico servico = convert(dto);
            servico = service.save(servico);
            return new ResponseEntity(ServicoDTO.create(servico), HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Atualiza serviço")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Serviço atualizado"),
            @ApiResponse(responseCode = "404", description = "Falha ao atualizar serviço")
    })
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody ServicoDTO dto) {
        if(!service.getServicoById(id).isPresent()){
            return new ResponseEntity("Serviço não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Servico servico = convert(dto);
            servico.setId(id);
            servico = service.save(servico);
            return ResponseEntity.ok(ServicoDTO.create(servico));
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @Operation(summary = "Deleta serviço")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Serviço deletado"),
            @ApiResponse(responseCode = "404", description = "Falha ao deletar serviço")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        Optional<Servico> servico = service.getServicoById(id);
        if(!servico.isPresent()){
            return new ResponseEntity("Serviço não encontrado", HttpStatus.NOT_FOUND);
        }
        try{
            service.delete(servico.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body("Não foi possível excluir o serviço: " + e.getMessage());
        }
    }

    public Servico convert(ServicoDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Servico servico = modelMapper.map(dto, Servico.class);
        return servico;
    }

}
