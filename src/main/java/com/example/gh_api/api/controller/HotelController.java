package com.example.gh_api.api.controller;

import com.example.gh_api.api.dto.HotelDTO;

import com.example.gh_api.api.dto.ServicoDTO;
import com.example.gh_api.exception.RegraNegocioException;
import com.example.gh_api.model.entity.Hotel;
import com.example.gh_api.model.entity.Servico;
import com.example.gh_api.service.HotelService;
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
public class HotelController {
    private final HotelService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Hotel> hotel = service.getAllHotels();
        return ResponseEntity.ok(hotel.stream().map(HotelDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable("id") Long id) {
        Optional<Hotel> hotel = service.getHotelById(id);
        if(!hotel.isPresent()){
            return new ResponseEntity("Hotel não encontrado ", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(hotel.map(HotelDTO::create));
    }

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

    public Hotel convert(HotelDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Hotel hotel = modelMapper.map(dto, Hotel.class);
        return hotel;
    }
}
