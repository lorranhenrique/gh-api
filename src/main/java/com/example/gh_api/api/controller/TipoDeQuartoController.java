package com.example.gh_api.api.controller;

import com.example.gh_api.api.dto.TipoDeQuartoDTO;
import com.example.gh_api.exception.RegraNegocioException;
import com.example.gh_api.model.entity.TipoDeQuarto;
import com.example.gh_api.service.TipoDeQuartoService;
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
@RequestMapping("/api/v1/tiposDeQuarto")
@RequiredArgsConstructor
@CrossOrigin

@Tag(name = "Tipo de quarto", description = "Gerenciador de tipos de quarto")
public class TipoDeQuartoController {

    private final TipoDeQuartoService service;

    @Operation( summary = "Busca tipos de quarto")
    @ApiResponses({
            @ApiResponse( responseCode = "200", description = "Tipos de quarto encontrados"),
            @ApiResponse( responseCode = "404", description = "Tipos de quarto não encontrados")
    })
    @GetMapping()
    public ResponseEntity get(){
        List<TipoDeQuarto> tipoDeQuartos = service.getAllTipoDeQuartos();
        return ResponseEntity.ok(tipoDeQuartos.stream().map(TipoDeQuartoDTO::create).collect(Collectors.toList()));
    }

    @Operation( summary = "Busca tipo de quarto pelo id")
    @ApiResponses({
            @ApiResponse( responseCode = "200", description = "Tipo de quarto encontrado"),
            @ApiResponse( responseCode = "404", description = "Tipo de quarto não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable("id") long id){
        Optional<TipoDeQuarto> tipoDeQuarto = service.getTipoDeQuartoById(id);
        if(!tipoDeQuarto.isPresent()){
            return new ResponseEntity("Tipo de quarto não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(tipoDeQuarto.map(TipoDeQuartoDTO::create));
    }
    @Operation( summary = "Cria tipo de quarto")
    @ApiResponses({
            @ApiResponse( responseCode = "200", description = "Tipo de quarto criado"),
            @ApiResponse( responseCode = "404", description = "Falha ao criar tipo de quarto")
    })
    @PostMapping
    public ResponseEntity post(@RequestBody TipoDeQuartoDTO dto){
        try{
            TipoDeQuarto tipoDeQuarto = convert(dto);
            tipoDeQuarto = service.save(tipoDeQuarto);
            return new ResponseEntity(tipoDeQuarto, HttpStatus.CREATED);
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation( summary = "Atualiza tipo de quarto")
    @ApiResponses({
            @ApiResponse( responseCode = "200", description = "Tipo de quarto atualizado"),
            @ApiResponse( responseCode = "404", description = "Falha ao atualizar tipo de quarto")
    })
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") long id, @RequestBody TipoDeQuartoDTO dto){
        if(!service.getTipoDeQuartoById(id).isPresent()){
            return new ResponseEntity("Tipo de quarto não encontrado", HttpStatus.NOT_FOUND);
        }
        try{
            TipoDeQuarto tipoDeQuarto = convert(dto);
            tipoDeQuarto.setId(id);
            tipoDeQuarto = service.save(tipoDeQuarto);
            return ResponseEntity.ok(tipoDeQuarto);
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation( summary = "Deleta tipo de quarto")
    @ApiResponses({
            @ApiResponse( responseCode = "200", description = "Tipo de quarto deletado"),
            @ApiResponse( responseCode = "404", description = "Falha ao deletar tipo de quarto")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") long id) {
        Optional<TipoDeQuarto> tipoDeQuarto = service.getTipoDeQuartoById(id);
        if(!tipoDeQuarto.isPresent()){
            return new ResponseEntity("Tipo de quarto não encontrado", HttpStatus.NOT_FOUND);
        }
        try{
            service.delete(tipoDeQuarto.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body("Não foi possível excluir o tipo de quarto: " + e.getMessage());
        }
    }

    public TipoDeQuarto convert(TipoDeQuartoDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        TipoDeQuarto tipoDeQuarto = modelMapper.map(dto, TipoDeQuarto.class);
        return tipoDeQuarto;
    }
}
