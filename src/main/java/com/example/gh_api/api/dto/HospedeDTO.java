package com.example.gh_api.api.dto;

import com.example.gh_api.model.entity.Hospede;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HospedeDTO {

    private Long id;
    private String nome;
    private String genero;
    private String dataNascimento;
    private String telefone;
    private String email;
    private String cpf;
    private String estado;
    private String cidade;
    private String cep;
    private String bairro;
    private String logradouro;
    private String numeroMoradia;
    private String complemento;

    public static HospedeDTO create(Hospede hospede) {
        ModelMapper modelMapper = new ModelMapper();
        HospedeDTO dto = modelMapper.map(hospede, HospedeDTO.class);
        dto.nome = hospede.getNome();
        dto.genero = hospede.getGenero();
        dto.dataNascimento = hospede.getDataNascimento();
        dto.telefone = hospede.getTelefone();
        dto.email = hospede.getEmail();
        dto.cpf = hospede.getCpf();
        dto.estado = hospede.getEstado();
        dto.cidade = hospede.getCidade();
        dto.cep = hospede.getCep();
        dto.bairro = hospede.getBairro();
        dto.logradouro = hospede.getLogradouro();
        dto.numeroMoradia = hospede.getNumeroMoradia();
        dto.complemento = hospede.getComplemento();
        return dto;
    }
}
