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
    private long id;
    private String nome;
    private String cep;
    private String estado;
    private String cidade;
    private String bairro;
    private String logradouro;
    private String numero;
    private String telefone;
    private String celular;
    private String email;
    private String imagem;

    public static CamaNoHotelDTO create(CamaNoHotel camaNoHotel) {
        ModelMapper modelMapper = new ModelMapper();
        CamaNoHotelDTO dto = modelMapper.map(camaNoHotel, CamaNoHotelDTO.class);
        return dto;
    }
}