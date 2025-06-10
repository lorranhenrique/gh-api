package com.example.gh_api.api.controller;

import com.example.gh_api.api.dto.ItemNoHotelDTO;

import com.example.gh_api.model.entity.ItemNoHotel;
import com.example.gh_api.service.ItemNoHotelService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/itens")
@RequiredArgsConstructor
@CrossOrigin
public class ItemNoHotelController {

    private final ItemNoHotelService service;

    public ItemNoHotel convert(ItemNoHotelDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        ItemNoHotel itemNoHotel = modelMapper.map(dto, ItemNoHotel.class);
        return itemNoHotel;
    }

    @GetMapping()
    public ResponseEntity get(){
        List<ItemNoHotel> itemNoHotels = service.getAllItems();
        return ResponseEntity.ok(itemNoHotels.stream().map(ItemNoHotelDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable("id") Long id){
        Optional<ItemNoHotel> itemNoHotel = service.getItemById(id);
        if(!itemNoHotel.isPresent()){
            return new ResponseEntity("Item não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(itemNoHotel.map(ItemNoHotelDTO::create));
    }


}
