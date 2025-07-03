package com.example.gh_api.api.controller;

import com.example.gh_api.api.dto.ItemDTO;

import com.example.gh_api.exception.RegraNegocioException;
import com.example.gh_api.model.entity.Item;
import com.example.gh_api.service.ItemService;
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
public class ItemController {
    private final ItemService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Item> item = service.getAllItems();
        return ResponseEntity.ok(item.stream().map(ItemDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable("id") Long id) {
        Optional<Item> item = service.getItemById(id);
        if(!item.isPresent()){
            return new ResponseEntity("Item não encontrado ", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(item.map(ItemDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody ItemDTO dto) {
        try {
            Item item = convert(dto);
            item = service.save(item);
            return new ResponseEntity(ItemDTO.create(item), HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody ItemDTO dto) {
        if(!service.getItemById(id).isPresent()){
            return new ResponseEntity("Item não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Item item = convert(dto);
            item.setId(id);
            item = service.save(item);
            return ResponseEntity.ok(ItemDTO.create(item));
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        Optional<Item> item = service.getItemById(id);
        if(!item.isPresent()){
            return new ResponseEntity("Item não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.delete(item.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body("Não foi possível excluir o item. " + e.getMessage());
        }
    }

    public Item convert(ItemDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Item item = modelMapper.map(dto, Item.class);
        return item;
    }
}
