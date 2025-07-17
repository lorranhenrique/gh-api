package com.example.gh_api.api.controller;

import com.example.gh_api.api.dto.ItemDTO;

import com.example.gh_api.exception.RegraNegocioException;
import com.example.gh_api.model.entity.Item;
import com.example.gh_api.service.ItemService;
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
@RequestMapping("/api/v1/itens")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name = "Item", description = "Gerenciador de itens")

public class ItemController {
    private final ItemService service;

    @Operation(summary = "Busca itens")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Itens encontrados"),
            @ApiResponse(responseCode = "404", description = "Itens não encontrados")
    })
    @GetMapping()
    public ResponseEntity get() {
        List<Item> item = service.getAllItems();
        return ResponseEntity.ok(item.stream().map(ItemDTO::create).collect(Collectors.toList()));
    }

    @Operation(summary = "Busca item pelo id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Item encontrado"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable("id") Long id) {
        Optional<Item> item = service.getItemById(id);
        if(!item.isPresent()){
            return new ResponseEntity("Item não encontrado ", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(item.map(ItemDTO::create));
    }

    @Operation(summary = "Cria item")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Item criado"),
            @ApiResponse(responseCode = "404", description = "Falha ao criar item")
    })
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

    @Operation(summary = "Atualiza item")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Item atualizado"),
            @ApiResponse(responseCode = "404", description = "Falha ao atualizar item")
    })
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

    @Operation(summary = "Deleta item")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Item deletado"),
            @ApiResponse(responseCode = "404", description = "Falha ao deletar item")
    })
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
