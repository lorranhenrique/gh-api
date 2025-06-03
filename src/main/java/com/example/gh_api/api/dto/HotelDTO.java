package com.example.gh_api.api.dto;

import com.example.gh_api.model.entity.Hotel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelDTO {

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

    public static HotelDTO create(Hotel hotel) {
        ModelMapper modelMapper = new ModelMapper();
        HotelDTO dto = modelMapper.map(hotel, HotelDTO.class);
        dto.nome = hotel.getNome();
        dto.cep = hotel.getCep();
        dto.estado = hotel.getEstado();
        dto.cidade = hotel.getCidade();
        dto.bairro = hotel.getBairro();
        dto.logradouro = hotel.getLogradouro();
        dto.numero = hotel.getNumero();
        dto.telefone = hotel.getTelefone();
        dto.celular = hotel.getCelular();
        dto.email = hotel.getEmail();
        dto.imagem = hotel.getImagem();
        return dto;
    }
}
