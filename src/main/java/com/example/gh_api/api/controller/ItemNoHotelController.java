package com.example.gh_api.api.controller;

import com.example.gh_api.api.dto.ItemNoHotelDTO;
import com.example.gh_api.exception.RegraNegocioException;
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

    @PostMapping
    public ResponseEntity post(@RequestBody ItemNoHotelDTO dto){
        try {           
            ItemNoHotel itemNoHotel = convert(dto);
            itemNoHotel = service.save(itemNoHotel);
            return new ResponseEntity(itemNoHotel, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody ItemNoHotelDTO dto){
        if(!service.getItemById(id).isPresent()){
            return new ResponseEntity("Item não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            ItemNoHotel itemNoHotel = convert(dto);
            itemNoHotel.setId(id);
            itemNoHotel = service.save(itemNoHotel);
            return ResponseEntity.ok(itemNoHotel);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<ItemNoHotel> item = service.getItemById(id);
        if (!item.isPresent()) {
            return new ResponseEntity("Item não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.delete(item.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body("Não foi possível excluir o item. " + e.getMessage());
        }
    }

    public ItemNoHotel convert(ItemNoHotelDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        ItemNoHotel itemNoHotel = modelMapper.map(dto, ItemNoHotel.class);
        return itemNoHotel;
    }

}
