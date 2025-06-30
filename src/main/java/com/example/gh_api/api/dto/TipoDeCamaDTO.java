package com.example.gh_api.api.dto;

import com.example.gh_api.model.entity.TipoDeCama;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoDeCamaDTO {

    private Long id;
    private String tipo;
    private int quantidadeAdultos;
    private int quantidadeCriancas;

    public static TipoDeCamaDTO create(TipoDeCama tipoDeCama) {
        ModelMapper modelMapper = new ModelMapper();
        TipoDeCamaDTO dto = modelMapper.map(tipoDeCama, TipoDeCamaDTO.class);
        dto.quantidadeAdultos = tipoDeCama.getQuantidadeAdultos();
        dto.quantidadeCriancas = tipoDeCama.getQuantidadeCriancas();
        dto.tipo = tipoDeCama.getTipo();
        return dto;
    }
}
