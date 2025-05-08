package com.example.gh_api.api.dto;

import com.example.gh_api.model.entity.Servico;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServicoDTO {

    private long id;
    private String nome;
    private String descricao;
    private float preco;
    private String imagem;
    private int minutosPorAgendamento;

    public static ServicoDTO create(Servico servico) {
        ModelMapper modelMapper = new ModelMapper();
        ServicoDTO dto = modelMapper.map(servico, ServicoDTO.class);
        return dto;
    }
}