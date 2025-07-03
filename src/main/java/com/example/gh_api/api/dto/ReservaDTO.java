package com.example.gh_api.api.dto;


import com.example.gh_api.model.entity.Reserva;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
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
    private Long idTipoDeQuarto;
    private String nomeTipoDeQuarto;
    private String dataChegada;
    private String dataSaida;

    public static ReservaDTO create(Reserva reserva) {
        ModelMapper modelMapper = new ModelMapper();
        ReservaDTO dto = modelMapper.map(reserva, ReservaDTO.class);
        dto.nomeHotel = reserva.getHotel().getNome();
        dto.nomeHospedeResponsavel = reserva.getHospede().getNome();
        dto.nomeTipoDeQuarto = reserva.getTipoDeQuarto().getTipo();
        dto.dataChegada = reserva.getDataChegada();
        dto.dataSaida = reserva.getDataSaida();
        dto.hotel = reserva.getHotel().getNome();

        return dto;
    }
}
