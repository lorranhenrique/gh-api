package com.example.gh_api.api.dto;

import com.example.gh_api.model.entity.Agendamento;
import com.example.gh_api.model.repository.HospedagemRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgendamentoDTO {
    private Long id;
    private Long idHospedagem;
    private String nomeHospede;
    private String hotel;
    private Long idTrabalhador;
    //private Long idAgenda;
    private String nomeTrabalhador;
    private String horarioInicio;
    private String data;
    private String quarto;
    private Long idServico;
    private String nomeServico;

    public static AgendamentoDTO create(Agendamento agendamento) {
        ModelMapper modelMapper = new ModelMapper();
        AgendamentoDTO dto = modelMapper.map(agendamento, AgendamentoDTO.class);
        dto.nomeServico = agendamento.getServico().getNome();
        dto.nomeTrabalhador = agendamento.getTrabalhador().getNome();
        dto.data = agendamento.getData();
        return dto;
    }
}
