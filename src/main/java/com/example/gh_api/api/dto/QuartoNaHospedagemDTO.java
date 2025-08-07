package com.example.gh_api.api.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;

import com.example.gh_api.model.entity.QuartoNaHospedagem;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuartoNaHospedagemDTO {

    private Long idQuarto;
    private String numeroQuarto;

    public static QuartoNaHospedagemDTO create(QuartoNaHospedagem quartoNaHospedagem) {
        ModelMapper modelMapper = new ModelMapper();

        QuartoNaHospedagemDTO dto = modelMapper.map(quartoNaHospedagem, QuartoNaHospedagemDTO.class);
        dto.idQuarto = quartoNaHospedagem.getQuarto().getId();
        dto.numeroQuarto = quartoNaHospedagem.getQuarto().getNumero();
        return dto;
    }
}
