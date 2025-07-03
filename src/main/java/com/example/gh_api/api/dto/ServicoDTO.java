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

    private Long id;
    private String nome;
    private String descricao;
    private Float preco;
    private String imagem;
    private Integer minutosPorAgendamento;

    public static ServicoDTO create(Servico servico) {
        ModelMapper modelMapper = new ModelMapper();
        ServicoDTO dto = modelMapper.map(servico, ServicoDTO.class);
        dto.nome = servico.getNome();
        dto.descricao = servico.getDescricao();
        dto.preco = servico.getPreco();
        dto.imagem = servico.getImagem();
        dto.minutosPorAgendamento = servico.getMinutosPorAgendamento();
        return dto;
    }
}
