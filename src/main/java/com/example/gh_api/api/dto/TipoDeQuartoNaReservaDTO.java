package com.example.gh_api.api.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;

import com.example.gh_api.model.entity.TipoDeQuartoNaReserva;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoDeQuartoNaReservaDTO {

    private Long idTipoDeQuarto;
    private int quantidade;

    public static TipoDeQuartoNaReservaDTO create(TipoDeQuartoNaReserva tipoDeQuartoNaReserva) {
        ModelMapper modelMapper = new ModelMapper();

        TipoDeQuartoNaReservaDTO dto = modelMapper.map(tipoDeQuartoNaReserva, TipoDeQuartoNaReservaDTO.class);
        dto.idTipoDeQuarto = tipoDeQuartoNaReserva.getTipoDeQuarto().getId();
            dto.quantidade = tipoDeQuartoNaReserva.getQuantidade();

        return dto;
    }
}
