package com.example.gh_api.api.controller;

import com.example.gh_api.api.dto.TipoDeCamaDTO;
import com.example.gh_api.exception.RegraNegocioException;
import com.example.gh_api.model.entity.TipoDeCama;
import com.example.gh_api.service.TipoDeCamaService;
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
@RequestMapping("/api/v1/tiposDeCama")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name = "Tipo de cama", description = "Gerenciador de tipo de cama")
public class TipoDeCamaController {

    private final TipoDeCamaService service;

    @Operation(summary = "Busca tipos de cama")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tipos de cama encontrados"),
            @ApiResponse(responseCode = "404", description = "Tipos de cama não encontrados")
    })
    @GetMapping()
    public ResponseEntity get(){
        List<TipoDeCama> tipoDeCamas = service.getTipoCamas();
        return ResponseEntity.ok(tipoDeCamas.stream().map(TipoDeCamaDTO::create).collect(Collectors.toList()));
    }

    @Operation(summary = "Busca tipo de cama pelo id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tipo de cama encontrado"),
            @ApiResponse(responseCode = "404", description = "Tipo de cama não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable("id") Long id){
        Optional<TipoDeCama> tipoDeCama = service.getTipoCamaById(id);
        if(!tipoDeCama.isPresent()){
            return new ResponseEntity("Tipo de cama  não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(tipoDeCama.map(TipoDeCamaDTO::create));
    }

    @Operation(summary = "Cria tipo de cama")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tipo de cama criado"),
            @ApiResponse(responseCode = "404", description = "Falha ao criar tipo de cama")
    })
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

    @Operation( summary = "Atualiza tipo de cama")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tipo de cama atualizado"),
            @ApiResponse(responseCode = "404", description = "Tipo de cama não encontrado")
    })
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

    @Operation(summary = "Deleta tipo de cama")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tipo de cama deletado"),
            @ApiResponse(responseCode = "404", description = "Falha ao deletar o tipo de cama")
    })
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
        // Configura o ModelMapper para ignorar ambiguidades, como os campos quantidade, quantidadeAdulto e quantidadeCrianca
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        TipoDeCama tipoDeCama = modelMapper.map(dto, TipoDeCama.class);
        return tipoDeCama;
    }
}
