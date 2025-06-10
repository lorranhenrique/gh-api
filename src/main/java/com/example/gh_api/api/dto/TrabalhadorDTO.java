package com.example.gh_api.api.dto;

import com.example.gh_api.model.entity.Trabalhador;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrabalhadorDTO {
    private long id;
    private String nome;
    private String telefone;
    private long idHotel;
    private String nomeHotel;
    private long idCargo;
    private String nomeCargo;

    public static TrabalhadorDTO create(Trabalhador trabalhador) {
        ModelMapper modelMapper = new ModelMapper();
        TrabalhadorDTO dto = modelMapper.map(trabalhador, TrabalhadorDTO.class);
        dto.nomeHotel = trabalhador.getHotel().getNome();
        dto.nomeCargo = trabalhador.getCargo().getNome();
        dto.nome = trabalhador.getNome();
        dto.telefone = trabalhador.getTelefone();

        return dto;
    }
}
