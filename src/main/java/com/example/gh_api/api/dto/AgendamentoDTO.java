package com.example.gh_api.api.dto;

import com.example.gh_api.model.entity.Agendamento;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgendamentoDTO {
    private long id;
    private String horarioInicio;
    private String data;
    private long idServico;
    private String nomeServico;
    private long idTrabalhador;
    private String nomeTrabalhador;
    private long idHospedagem;
    private String nomeHospede;
    private String quarto;

    public static AgendamentoDTO create(Agendamento agendamento) {
        ModelMapper modelMapper = new ModelMapper();
        AgendamentoDTO dto = modelMapper.map(agendamento, AgendamentoDTO.class);
        dto.nomeServico = agendamento.getServico().getNome();
        dto.nomeTrabalhador = agendamento.getTrabalhador().getNome();
        dto.nomeHospede = agendamento.getHospedagem().getHospede().getNome();
        // quando completar hospedagem, adiciona o quarto aqui
        return dto;
    }
}
