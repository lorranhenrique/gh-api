package com.example.gh_api.api.dto;

import com.example.gh_api.model.entity.Hospedagem;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HospedagemDTO {
    private long id;

    private String checkIn;
    private String checkOut;
    private long idHospede;
    private String nomeHospede;
    private int adultos;
    private int criancas;
    private long idHotel;
    private String nomeHotel;

    public static HospedagemDTO create(Hospedagem hospedagem) {
        ModelMapper modelMapper = new ModelMapper();
        HospedagemDTO dto = modelMapper.map(hospedagem, HospedagemDTO.class);
        dto.nomeHospede = hospedagem.getHospede().getNome();
        dto.nomeHotel = hospedagem.getHotel().getNome();
        return dto;
    }
}
