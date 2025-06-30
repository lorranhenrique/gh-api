package com.example.gh_api.api.dto;

import com.example.gh_api.model.entity.CamaNoHotel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CamaNoHotelDTO {
    private Long id;
    private Long idTipoDeCama;
    private String tipo;
    private int quantidade;
    private Long idHotel;
    private String hotel;

    public static CamaNoHotelDTO create(CamaNoHotel camaNoHotel) {
        ModelMapper modelMapper = new ModelMapper();
        CamaNoHotelDTO dto = modelMapper.map(camaNoHotel, CamaNoHotelDTO.class);
        dto.tipo = camaNoHotel.getTipoDeCama().getTipo();
        dto.quantidade = camaNoHotel.getQuantidade();
        dto.hotel = camaNoHotel.getHotel().getNome();
        return dto;
    }
}
