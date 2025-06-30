package com.example.gh_api.api.dto;

import com.example.gh_api.model.entity.Hospedagem;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
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
    private int adultos;
    private int criancas;
    private Long idHotel;
    private int quantidadeDeQuartos;
    private String nomeHotel;
    private String camaExtra;
    private String itemExtra;
    private Long idQuarto;
    private String numeroQuarto;

    public static HospedagemDTO create(Hospedagem hospedagem) {
        ModelMapper modelMapper = new ModelMapper();
        HospedagemDTO dto = modelMapper.map(hospedagem, HospedagemDTO.class);
        dto.nomeHospede = hospedagem.getHospede().getNome();
        dto.nomeHotel = hospedagem.getHotel().getNome();
        dto.checkIn = hospedagem.getCheckIn();
        dto.checkOut = hospedagem.getCheckOut();
        dto.adultos = hospedagem.getAdultos();
        dto.criancas = hospedagem.getCriancas();
        dto.quantidadeDeQuartos = hospedagem.getQuantidadeDeQuartos();
        if (hospedagem.getCamasExtrasNaReserva() != null) {
        dto.camaExtra = hospedagem.getCamasExtrasNaReserva().getCamaNoHotel().getTipoDeCama() != null ? 
                       hospedagem.getCamasExtrasNaReserva().getCamaNoHotel().getTipoDeCama().getTipo() : null;
        }
        if (hospedagem.getItensNaReserva() != null && hospedagem.getItensNaReserva().getItemNoHotel() != null) {
            dto.itemExtra = hospedagem.getItensNaReserva().getItemNoHotel().getItem().getNome();
        }
        dto.numeroQuarto = hospedagem.getQuarto().getNumero();

        return dto;
    }
}
