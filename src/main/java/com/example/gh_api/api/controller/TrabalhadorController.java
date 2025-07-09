package com.example.gh_api.api.controller;

import com.example.gh_api.api.dto.TrabalhadorDTO;
import com.example.gh_api.exception.RegraNegocioException;
import com.example.gh_api.model.entity.Trabalhador;
import com.example.gh_api.service.TrabalhadorService;
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
@RequestMapping("/api/v1/trabalhadores")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name = "Trabalhadores", description = "Gerenciador de trabalhadores")
public class TrabalhadorController {

    private final TrabalhadorService service;

    @Operation( summary = "Busca trabalhadores")
    @ApiResponses({
            @ApiResponse( responseCode = "200", description = "Trabalhadores encontrados"),
            @ApiResponse( responseCode = "404", description = "Trabalhadores não encontrados")
    })
    @GetMapping()
    public ResponseEntity get(){
        List<Trabalhador> trabalhadores = service.getAllTrabalhadores();
        return ResponseEntity.ok(trabalhadores.stream().map(TrabalhadorDTO::create).collect(Collectors.toList()));
    }

    @Operation( summary = "Busca trabalhador pelo id")
    @ApiResponses({
            @ApiResponse( responseCode = "200", description = "Trabalhador encontrado"),
            @ApiResponse( responseCode = "404", description = "Trabalhador não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable("id") Long id){
        Optional<Trabalhador> trabalhador = service.getTrabalhadorById(id);
        if(!trabalhador.isPresent()){
            return new ResponseEntity("Trabalhador não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(trabalhador.map(TrabalhadorDTO::create));
    }

    @Operation( summary = "Cria trabalhador")
    @ApiResponses({
            @ApiResponse( responseCode = "200", description = "Trabalhador criado"),
            @ApiResponse( responseCode = "404", description = "Falha ao criar trabalhador")
    })
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

    @Operation( summary = "Atualiza trabalhador")
    @ApiResponses({
            @ApiResponse( responseCode = "200", description = "Trabalhador alterado"),
            @ApiResponse( responseCode = "404", description = "Falha ao alterar trabalhador")
    })
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

    @Operation( summary = "Deleta trabalhador")
    @ApiResponses({
            @ApiResponse( responseCode = "200", description = "Trabalhador deletado"),
            @ApiResponse( responseCode = "404", description = "Falha ao deletar o trabalhador")
    })
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
