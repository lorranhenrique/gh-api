package com.example.gh_api.api.dto;

import com.example.gh_api.model.entity.Reserva;
import com.example.gh_api.api.dto.TipoDeQuartoNaReservaDTO;
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
public class ReservaDTO {

    private Long id;

    private Long idHotel;
    private String hotel;
    private String nomeHotel;
    private Long idHospede;
    private String nomeHospedeResponsavel;
    private Long idHospedagem;
    private String dataChegada;
    private String dataSaida;
    private List<Map<Long, Integer>> tiposDeQuartoNaReserva;
    private List<TipoDeQuartoNaReservaDTO> tipoDeQuartoNaReservaDTO; // para cadastro de reserva
    private List<String> quartosNaReserva; // para exibição de reservas

    public static ReservaDTO create(Reserva reserva) {

        ReservaDTO dto = new ReservaDTO();
        dto.setId(reserva.getId());
        dto.setIdHotel(reserva.getHotel().getId());
        dto.setHotel(reserva.getHotel().getNome());
        dto.setIdHospede(reserva.getHospede().getId());
        dto.setNomeHospedeResponsavel(reserva.getHospede().getNome());
        dto.setIdHospedagem(reserva.getHospedagem() != null ? reserva.getHospedagem().getId() : null);
        dto.setDataChegada(reserva.getDataChegada());
        dto.setDataSaida(reserva.getDataSaida());

        dto.tiposDeQuartoNaReserva = reserva.getTipoDeQuartoNaReserva().stream()
                .map(nToN -> {
                    Map<Long, Integer> tipoDeQuartoMap = Map.of(
                            nToN.getTipoDeQuarto().getId(), nToN.getQuantidade()
                    );
                    return tipoDeQuartoMap;
                })
                .collect(Collectors.toList());

        dto.setQuartosNaReserva(reserva.getTipoDeQuartoNaReserva().stream()
                .map(nToN -> nToN.getQuantidade() + "x " + nToN.getTipoDeQuarto().getTipo())
                .collect(Collectors.toList()));
        return dto;
    }
}
