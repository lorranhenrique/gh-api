package com.example.gh_api.api.dto;

import com.example.gh_api.model.entity.Item;
import org.modelmapper.ModelMapper;

public class ItemDTO {

    private Long id;
    private String nome;
    private String imagem;

    public static ItemDTO create(Item item) {
        ModelMapper modelMapper = new ModelMapper();
        ItemDTO dto = modelMapper.map(item, ItemDTO.class);
        dto.imagem = item.getImagem();
        dto.nome = item.getNome();

        return dto;
    }
}
