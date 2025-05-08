package com.example.gh_api.api.dto;

import com.example.gh_api.model.entity.Quarto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuartoDTO {

    private long id;
    private long idHotel;
    private String nomeHotel;
    private long idTipoDeQuarto;
    private String nomeTipoDeQuarto;
    private String numero;
    private String situacao;

    public static QuartoDTO create(Quarto quarto) {
        ModelMapper modelMapper = new ModelMapper();
        QuartoDTO dto = modelMapper.map(quarto, QuartoDTO.class);
        dto.nomeHotel = quarto.getHotel().getNome();
        dto.nomeTipoDeQuarto = quarto.getTipoDeQuarto().getTipo();
        return dto;
    }
}
