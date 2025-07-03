package com.example.gh_api.api.controller;

import com.example.gh_api.api.dto.ReservaDTO;

import com.example.gh_api.model.entity.Reserva;
import com.example.gh_api.service.ReservaService;
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
@RequestMapping("/api/v1/reservas")
@RequiredArgsConstructor
@CrossOrigin
public class ReservaController {

    private final ReservaService service;

    @GetMapping()
    public ResponseEntity get(){
        List<Reserva> reserva = service.getAllReservas();
        return ResponseEntity.ok(reserva.stream().map(ReservaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable("id") Long id){
        Optional<Reserva> reserva = service.getReservasById(id);
        if(!reserva.isPresent()){
            return new ResponseEntity("Reserva não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(reserva.map(ReservaDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody ReservaDTO dto){
        try {
            Reserva reserva = convert(dto);
            reserva = service.save(reserva);
            return new ResponseEntity(ReservaDTO.create(reserva), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody ReservaDTO dto){
        if(!service.getReservasById(id).isPresent()){
            return new ResponseEntity("Reserva não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Reserva reserva = convert(dto);
            reserva.setId(id);
            reserva = service.save(reserva);
            return ResponseEntity.ok(ReservaDTO.create(reserva));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Reserva> reserva = service.getReservasById(id);
        if (!reserva.isPresent()) {
            return new ResponseEntity("Reserva não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.delete(reserva.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body("Não foi possível excluir a reserva. " + e.getMessage());
        }
    }

    public Reserva convert(ReservaDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Reserva reserva = modelMapper.map(dto, Reserva.class);
        return reserva;
    }
}
