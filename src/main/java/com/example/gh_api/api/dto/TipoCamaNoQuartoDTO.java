package com.example.gh_api.api.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;

import com.example.gh_api.model.entity.TipoCamaNoQuarto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoCamaNoQuartoDTO {

    private Long idTipoDeCama;
    private int quantidade;

    public static TipoCamaNoQuartoDTO create(TipoCamaNoQuarto tipoCamaNoQuarto) {
        ModelMapper modelMapper = new ModelMapper();

        TipoCamaNoQuartoDTO dto = modelMapper.map(tipoCamaNoQuarto, TipoCamaNoQuartoDTO.class);
        dto.idTipoDeCama = tipoCamaNoQuarto.getTipoDeCama().getId();
            dto.quantidade = tipoCamaNoQuarto.getQuantidade();

        return dto;
    }
}
