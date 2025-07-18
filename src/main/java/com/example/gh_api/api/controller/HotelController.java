package com.example.gh_api.api.controller;

import com.example.gh_api.api.dto.HotelDTO;

import com.example.gh_api.api.dto.ServicoDTO;
import com.example.gh_api.exception.RegraNegocioException;
import com.example.gh_api.model.entity.Hotel;
import com.example.gh_api.model.entity.Servico;
import com.example.gh_api.service.HotelService;
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
@RequestMapping("/api/v1/hoteis")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name = "Hotel", description = "Gerenciador de hotéis")

public class HotelController {
    private final HotelService service;

    @Operation(summary = "Busca hotéis")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Hotéis encontrados"),
            @ApiResponse(responseCode = "404", description = "Hotéis não encontrados")
    })
    @GetMapping()
    public ResponseEntity get() {
        List<Hotel> hotel = service.getAllHotels();
        return ResponseEntity.ok(hotel.stream().map(HotelDTO::create).collect(Collectors.toList()));
    }

    @Operation(summary = "Busca hotel pelo id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Hotel encontrado"),
            @ApiResponse(responseCode = "404", description = "Hotel não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable("id") Long id) {
        Optional<Hotel> hotel = service.getHotelById(id);
        if(!hotel.isPresent()){
            return new ResponseEntity("Hotel não encontrado ", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(hotel.map(HotelDTO::create));
    }

    @Operation(summary = "Cria hotel")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Hotel criado"),
            @ApiResponse(responseCode = "404", description = "Falha ao criar hotel")
    })
    @PostMapping
    public ResponseEntity post(@RequestBody HotelDTO dto) {
        try {
            Hotel hotel = convert(dto);
            hotel = service.save(hotel);
            return new ResponseEntity(HotelDTO.create(hotel), HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Atualiza hotel")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Hotel atualizado"),
            @ApiResponse(responseCode = "404", description = "Falha ao atualizar hotel")
    })
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody HotelDTO dto) {
        if(!service.getHotelById(id).isPresent()){
            return new ResponseEntity("Hotel não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Hotel hotel = convert(dto);
            hotel.setId(id);
            hotel = service.save(hotel);
            return ResponseEntity.ok(HotelDTO.create(hotel));
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Deleta hotel")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Hotel deletado"),
            @ApiResponse(responseCode = "404", description = "Falha ao deletar hotel")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        Optional<Hotel> hotel = service.getHotelById(id);
        if(!hotel.isPresent()){
            return new ResponseEntity("Hotel não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.delete(hotel.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body("Não foi possível excluir o hotel. " + e.getMessage());
        }
    }

    public Hotel convert(HotelDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Hotel hotel = modelMapper.map(dto, Hotel.class);
        return hotel;
    }
}
