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

    private long id;

    private long idHotel;
    private String nomeHotel;
    private long idHospede;
    private String nomeHospedeResponsavel;
    private long idHospedagem;
    private long idTipoDeQuarto;
    private String nomeTipoDeQuarto;
    private String dataChegada;
    private String dataSaida;

    public static ReservaDTO create(Reserva reserva) {
        ModelMapper modelMapper = new ModelMapper();
        ReservaDTO dto = modelMapper.map(reserva, ReservaDTO.class);
        dto.nomeHotel = reserva.getHotel().getNome();
        dto.nomeHospedeResponsavel = reserva.getHospede().getNome();
        dto.nomeTipoDeQuarto = reserva.getTipoDeQuarto().getTipo();
        return dto;
    }
}
