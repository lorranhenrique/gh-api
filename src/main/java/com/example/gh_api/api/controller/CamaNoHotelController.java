package com.example.gh_api.api.controller;

import com.example.gh_api.api.dto.CamaNoHotelDTO;

import com.example.gh_api.exception.RegraNegocioException;

import com.example.gh_api.model.entity.CamaNoHotel;
import com.example.gh_api.model.entity.TipoDeCama;
import com.example.gh_api.model.entity.Hotel;
import com.example.gh_api.service.CamaNoHotelService;
import com.example.gh_api.service.TipoDeCamaService;
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
@RequestMapping("/api/v1/camas")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name = "Cama no hotel", description = "Gerenciador de camas no hotel")

public class CamaNoHotelController {

    private final CamaNoHotelService service;
    private final TipoDeCamaService tipoDeCamaService;
    private final HotelService hotelService;

    @Operation(summary = "Busca camas no hotel")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Camas no hotel encontradas"),
            @ApiResponse(responseCode = "404", description = "Camas no hotel não encontradas")
    })
    @GetMapping()
    public ResponseEntity get(){
        List<CamaNoHotel> camaNoHotels = service.getAllCamas();
        return ResponseEntity.ok(camaNoHotels.stream().map(CamaNoHotelDTO::create).collect(Collectors.toList()));
    }

    @Operation(summary = "Busca cama no hotel pelo id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cama no hotel encontrada"),
            @ApiResponse(responseCode = "404", description = "Cama no hotel não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable Long id){
        Optional<CamaNoHotel> camaNoHotel = service.getCamaById(id);
        if(!camaNoHotel.isPresent()){
            return new ResponseEntity("Cama não encontrada" ,HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(camaNoHotel.map(CamaNoHotelDTO::create));
    }

    @Operation(summary = "Cria cama no hotel")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cama no hotel criada"),
            @ApiResponse(responseCode = "404", description = "Falha ao criar cama no hotel")
    })
    @PostMapping
    public ResponseEntity post(@RequestBody CamaNoHotelDTO dto){
        try{
            CamaNoHotel camaNoHotel = convert(dto);
            camaNoHotel = service.save(camaNoHotel);
            return new ResponseEntity(camaNoHotel, HttpStatus.CREATED);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Atualiza cama no hotel")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cama no hotel atualizada"),
            @ApiResponse(responseCode = "404", description = "Falha ao atualizar cama no hotel")
    })
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody CamaNoHotelDTO dto) {
        if (!service.getCamaById(id).isPresent()) {
            return new ResponseEntity("Cama não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            CamaNoHotel camaNoHotel = convert(dto);
            camaNoHotel.setId(id);
            camaNoHotel = service.save(camaNoHotel);
            return ResponseEntity.ok(camaNoHotel);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Deleta cama no hotel")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cama no hotel deletada"),
            @ApiResponse(responseCode = "404", description = "Falha ao deletar cama no hotel")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        Optional<CamaNoHotel> camaNoHotel = service.getCamaById(id);
        if (!camaNoHotel.isPresent()) {
            return new ResponseEntity("Cama não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.delete(camaNoHotel.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body("Não foi possível excluir a cama. " + e.getMessage());
        }
    }

    public CamaNoHotel convert(CamaNoHotelDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        CamaNoHotel camaNoHotel = modelMapper.map(dto, CamaNoHotel.class);
        if (dto.getIdTipoDeCama() != null) {
            Optional<TipoDeCama> tipoDeCama = tipoDeCamaService.getTipoCamaById(dto.getIdTipoDeCama());
            if (!tipoDeCama.isPresent()) {
                throw new RegraNegocioException("Tipo de cama não encontrado");
            }
            camaNoHotel.setTipoDeCama(tipoDeCama.get());
        }
        if (dto.getIdHotel() != null) {
            Optional<Hotel> hotel = hotelService.getHotelById(dto.getIdHotel());
            if (!hotel.isPresent()) {
                throw new RegraNegocioException("Hotel não encontrado");
            }
            camaNoHotel.setHotel(hotel.get());
        }
        return camaNoHotel;
    }
}
