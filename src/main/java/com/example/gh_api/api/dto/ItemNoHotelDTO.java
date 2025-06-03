package com.example.gh_api.api.dto;

import com.example.gh_api.model.entity.ItemNoHotel;
import com.example.gh_api.model.entity.Servico;
import com.example.gh_api.model.entity.TipoDeQuarto;
import org.modelmapper.ModelMapper;


public class ItemNoHotelDTO {

    private long id;
    private int quantidade;
    private long idItem;
    private String nomeItem;
    private String imagemItem;
    private String hotel;
    private float preco;

    public static ItemNoHotelDTO create(ItemNoHotel itemNoHotel) {
        ModelMapper modelMapper = new ModelMapper();
        ItemNoHotelDTO dto = modelMapper.map(itemNoHotel, ItemNoHotelDTO.class);
        dto.nomeItem = itemNoHotel.getItem().getNome();
        dto.imagemItem = itemNoHotel.getItem().getImagem();
        dto.hotel = itemNoHotel.getHotel().getNome();
        dto.preco = itemNoHotel.getPreco();
        dto.quantidade = itemNoHotel.getEstoque();

        return dto;
    }
}
