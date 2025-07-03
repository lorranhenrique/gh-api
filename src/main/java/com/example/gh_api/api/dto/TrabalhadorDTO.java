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
    private Long id;
    private String nome;
    private String telefone;
    private Long idHotel;
    private String nomeHotel;
    private Long idCargo;
    private String nomeCargo;
    private String genero;
    private String email;
    private String CPF;
    private String estado;
    private String cidade;
    private String cep;
    private String bairro;
    private String logradouro;
    private String numeroMoradia;
    private String complemento;
    private String dataNascimento;

    public static TrabalhadorDTO create(Trabalhador trabalhador) {
        ModelMapper modelMapper = new ModelMapper();
        TrabalhadorDTO dto = modelMapper.map(trabalhador, TrabalhadorDTO.class);
        dto.nomeHotel = trabalhador.getHotel().getNome();
        dto.nomeCargo = trabalhador.getCargo().getNome();
        dto.nome = trabalhador.getNome();
        dto.telefone = trabalhador.getTelefone();
        dto.genero = trabalhador.getGenero();
        dto.email = trabalhador.getEmail();
        dto.CPF = trabalhador.getCpf();
        dto.estado = trabalhador.getEstado();
        dto.cidade = trabalhador.getCidade();
        dto.cep = trabalhador.getCep();
        dto.bairro = trabalhador.getBairro();
        dto.logradouro = trabalhador.getLogradouro();
        dto.numeroMoradia = trabalhador.getNumeroMoradia();
        dto.complemento = trabalhador.getComplemento();
        dto.dataNascimento = trabalhador.getDataNascimento();
        return dto;
    }
}
