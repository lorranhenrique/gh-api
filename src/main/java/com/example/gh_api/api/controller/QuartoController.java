package com.example.gh_api.api.controller;

import com.example.gh_api.api.dto.QuartoDTO;
import com.example.gh_api.exception.RegraNegocioException;
import com.example.gh_api.model.entity.Quarto;
import com.example.gh_api.service.QuartoService;
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
@RequestMapping("/api/v1/quartos")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name = "Quarto", description = "Gerenciador de quartos")

public class QuartoController {

    private final QuartoService service;

    @Operation(summary = "Busca quartos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Quartos encontrados"),
            @ApiResponse(responseCode = "404", description = "Quartos não encontrados")
    })
    @GetMapping()
    public ResponseEntity get(){
        List<Quarto> quartos = service.getAllQuartos();
        return ResponseEntity.ok(quartos.stream().map(QuartoDTO::create).collect(Collectors.toList()));
    }

    @Operation(summary = "Busca quarto pelo id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Quarto encontrado"),
            @ApiResponse(responseCode = "404", description = "Quarto não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable("id") Long id){
        Optional<Quarto> quarto = service.getQuartoById(id);
        if(!quarto.isPresent()){
            return new ResponseEntity("Quarto não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(quarto.map(QuartoDTO::create));
    }

    @Operation(summary = "Cria quarto")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Quarto criado"),
            @ApiResponse(responseCode = "404", description = "Falha ao criar quarto")
    })
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

    @Operation(summary = "Atualiza quarto")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Quarto atualizado"),
            @ApiResponse(responseCode = "404", description = "Falha ao atualizar quarto")
    })
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

    @Operation(summary = "Deleta quarto")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Quarto deletado"),
            @ApiResponse(responseCode = "404", description = "Falha ao deletar quarto")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        Optional<Quarto> quarto = service.getQuartoById(id);
        if(!quarto.isPresent()){
            return new ResponseEntity("Quarto não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.delete(quarto.get());
            return new ResponseEntity("Quarto deletado com sucesso", HttpStatus.OK);
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body("Não foi possível deletar o quarto: " + e.getMessage());
        }
    }

    public Quarto convert(QuartoDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Quarto quarto = modelMapper.map(dto, Quarto.class);
        return quarto;
    }
}
