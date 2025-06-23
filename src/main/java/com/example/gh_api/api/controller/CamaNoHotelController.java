package com.example.gh_api.api.controller;

import com.example.gh_api.api.dto.CamaNoHotelDTO;
import com.example.gh_api.exception.RegraNegocioException;
import com.example.gh_api.model.entity.CamaNoHotel;
import com.example.gh_api.service.CamaNoHotelService;
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

public class CamaNoHotelController {

    private final CamaNoHotelService service;

    @GetMapping()
    public ResponseEntity get(){
        List<CamaNoHotel> camaNoHotels = service.getAllCamas();
        return ResponseEntity.ok(camaNoHotels.stream().map(CamaNoHotelDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable Long id){
        Optional<CamaNoHotel> camaNoHotel = service.getCamaById(id);
        if(!camaNoHotel.isPresent()){
            return new ResponseEntity("Cama não encontrada" ,HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(camaNoHotel.map(CamaNoHotelDTO::create));
    }

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

    public CamaNoHotel convert(CamaNoHotelDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        CamaNoHotel camaNoHotel = modelMapper.map(dto, CamaNoHotel.class);
        return camaNoHotel;
    }
}
