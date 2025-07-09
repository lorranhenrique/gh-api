package com.example.gh_api.api.controller;

import com.example.gh_api.api.dto.ReservaDTO;
import com.example.gh_api.model.entity.Reserva;
import com.example.gh_api.service.ReservaService;
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
@RequestMapping("/api/v1/reservas")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name = "Reserva", description = "Gerenciador de reservas")
public class ReservaController {

    private final ReservaService service;

    @Operation(summary = "Busca reservas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reservas encontradas"),
            @ApiResponse(responseCode = "404", description = "Reservas não encontradas")
    })
    @GetMapping()
    public ResponseEntity get(){
        List<Reserva> reserva = service.getAllReservas();
        return ResponseEntity.ok(reserva.stream().map(ReservaDTO::create).collect(Collectors.toList()));
    }

    @Operation(summary = "Busca reserva pelo id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reserva encontrada"),
            @ApiResponse(responseCode = "404", description = "Reserva não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable("id") Long id){
        Optional<Reserva> reserva = service.getReservasById(id);
        if(!reserva.isPresent()){
            return new ResponseEntity("Reserva não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(reserva.map(ReservaDTO::create));
    }

    @Operation(summary = "Cria reserva")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reserva criada"),
            @ApiResponse(responseCode = "404", description = "Falha ao criar reserva")
    })
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

    @Operation(summary = "Atualiza reserva")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reserva atualizada"),
            @ApiResponse(responseCode = "404", description = "Falha ao atualizar reserva")
    })
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

    @Operation(summary = "Deleta reserva")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reserva deletada"),
            @ApiResponse(responseCode = "404", description = "Falha ao deletar reserva")
    })
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
