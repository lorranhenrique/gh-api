package com.example.gh_api.api.controller;

import com.example.gh_api.api.dto.AgendamentoDTO;
import com.example.gh_api.exception.RegraNegocioException;
import com.example.gh_api.model.entity.Agendamento;
import com.example.gh_api.service.AgendamentoService;
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
@RequestMapping("/api/v1/agendamentos")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name = "Agendamento", description = "Gerenciador de agendamentos")

public class AgendamentoController {

    private final AgendamentoService service;


    @Operation(summary = "Busca agendamentos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Agendamentos encontrados"),
            @ApiResponse(responseCode = "404", description = "Agendamentos não encontrados")
    })
    @GetMapping()
    public ResponseEntity get(){
        List<Agendamento> agendamentos = service.getAllAgendamento();
        return ResponseEntity.ok(agendamentos.stream().map(AgendamentoDTO::create).collect(Collectors.toList()));
    }

    @Operation(summary = "Busca agendamento pelo id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Agendamento encontrado"),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable("id") Long id){
        Optional<Agendamento> agendamento = service.getAgendamentoById(id);
        if(!agendamento.isPresent()){
            return new ResponseEntity("Agendamento não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(agendamento.map(AgendamentoDTO::create));
    }

    @Operation(summary = "Cria agendamento")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Agendamento criado"),
            @ApiResponse(responseCode = "404", description = "Falha ao criar agendamento")
    })
    @PostMapping
    public ResponseEntity post(@RequestBody AgendamentoDTO dto){
        try {
            //Agendamento agendamento = convert(dto);
            Agendamento agendamento = service.save(dto); // antes era passado agendamento
            return new ResponseEntity(AgendamentoDTO.create(agendamento), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Atualiza agendamento")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Agendamento atualizado"),
            @ApiResponse(responseCode = "404", description = "Falha ao atualizar agendamento")
    })
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody AgendamentoDTO dto){
        if(!service.getAgendamentoById(id).isPresent()){
            return new ResponseEntity("Agendamento não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            //Agendamento agendamento = convert(dto);
            Agendamento agendamento = service.save(dto);
            agendamento.setId(id);
            //agendamento = repository.save(agendamento);
            return ResponseEntity.ok(AgendamentoDTO.create(agendamento));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Deleta agendamento")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Agendamento deletado"),
            @ApiResponse(responseCode = "404", description = "Falha ao deletar agendamento")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        Optional<Agendamento> agendamento = service.getAgendamentoById(id);
        if(!agendamento.isPresent()){
            return new ResponseEntity("Agendamento não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.delete(agendamento.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body("Não foi possível excluir o agendamento. " + e.getMessage());
        }
    }

    public Agendamento convert(AgendamentoDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Agendamento agendamento = modelMapper.map(dto, Agendamento.class);
        return agendamento;
    }

}
