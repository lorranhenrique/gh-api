package com.example.gh_api.api.dto;

import com.example.gh_api.model.entity.Hospedagem;
import com.example.gh_api.model.repository.QuartoRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HospedagemDTO {
    private Long id;

    private String checkIn;
    private String checkOut;
    private Long idHospede;
    private String nomeHospede;
    private Integer adultos;
    private Integer criancas;
    private Long idHotel;
    private String nomeHotel;
    private List<Long> quartoNaHospedagem; // cadastro
    private List<QuartoNaHospedagemDTO> quartoNaHospedagemDTO; // inserir
    private List<String> quartos; // exibir
    private Float valorQuartos;
    private Float valorServicos;
    private Float valorTotal;
    private List<AgendamentoDTO> agendamentos;

    public static HospedagemDTO create(Hospedagem hospedagem) {
        HospedagemDTO dto = new HospedagemDTO();
        dto.setId(hospedagem.getId());
        dto.setIdHospede(hospedagem.getHospede().getId());
        dto.nomeHospede = hospedagem.getHospede().getNome();
        dto.setIdHotel(hospedagem.getHotel().getId());
        dto.nomeHotel = hospedagem.getHotel().getNome();
        dto.checkIn = hospedagem.getCheckIn();
        dto.checkOut = hospedagem.getCheckOut();
        dto.adultos = hospedagem.getAdultos();
        dto.criancas = hospedagem.getCriancas();
        dto.quartoNaHospedagem = hospedagem.getQuartoNaHospedagem().stream()
            .map(quarto -> quarto.getQuarto().getId())
            .collect(Collectors.toList());

        dto.setQuartos(hospedagem.getQuartoNaHospedagem().stream()
            .map(nToN -> nToN.getQuarto().getNumero() + " - " + nToN.getQuarto().getTipoDeQuarto().getTipo())
            .collect(Collectors.toList()));
        dto.valorQuartos = hospedagem.getQuartoNaHospedagem().stream()
            .map(quarto -> quarto.getQuarto().getTipoDeQuarto().getPreco())
            .reduce(0f, Float::sum);
        dto.agendamentos = hospedagem.getAgendamentos().stream()
            .map(AgendamentoDTO::create)
            .collect(Collectors.toList());
        dto.quartoNaHospedagemDTO = hospedagem.getQuartoNaHospedagem().stream()
            .map(QuartoNaHospedagemDTO::create)
            .collect(Collectors.toList());

        dto.valorServicos = hospedagem.getAgendamentos().stream()
            .map(agendamento -> agendamento.getServico().getPreco())
            .reduce(0f, Float::sum);

        dto.valorTotal = dto.valorQuartos + dto.valorServicos;

        return dto;
    }
}
