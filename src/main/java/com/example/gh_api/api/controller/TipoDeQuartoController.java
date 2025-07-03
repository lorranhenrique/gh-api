package com.example.gh_api.api.controller;

import com.example.gh_api.api.dto.TipoDeQuartoDTO;
import com.example.gh_api.exception.RegraNegocioException;
import com.example.gh_api.model.entity.TipoDeQuarto;
import com.example.gh_api.service.TipoDeQuartoService;
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

public class TipoDeQuartoController {

    private final TipoDeQuartoService service;

    @GetMapping()
    public ResponseEntity get(){
        List<TipoDeQuarto> tipoDeQuartos = service.getAllTipoDeQuartos();
        return ResponseEntity.ok(tipoDeQuartos.stream().map(TipoDeQuartoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable("id") long id){
        Optional<TipoDeQuarto> tipoDeQuarto = service.getTipoDeQuartoById(id);
        if(!tipoDeQuarto.isPresent()){
            return new ResponseEntity("Tipo de quarto não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(tipoDeQuarto.map(TipoDeQuartoDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody TipoDeQuartoDTO dto){
        try{
            //TipoDeQuarto tipoDeQuarto = convert(dto);
            TipoDeQuarto tipoDeQuarto = service.save(dto);
            return new ResponseEntity(tipoDeQuarto, HttpStatus.CREATED);
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") long id, @RequestBody TipoDeQuartoDTO dto){
        if(!service.getTipoDeQuartoById(id).isPresent()){
            return new ResponseEntity("Tipo de quarto não encontrado", HttpStatus.NOT_FOUND);
        }
        try{
            //TipoDeQuarto tipoDeQuarto = convert(dto);
            //tipoDeQuarto.setId(id);
            TipoDeQuarto tipoDeQuarto = service.save(dto);
            return ResponseEntity.ok(tipoDeQuarto);
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

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

    /*
    public TipoDeQuarto convert(TipoDeQuartoDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        TipoDeQuarto tipoDeQuarto = modelMapper.map(dto, TipoDeQuarto.class);
        return tipoDeQuarto;
    }
    */
}
