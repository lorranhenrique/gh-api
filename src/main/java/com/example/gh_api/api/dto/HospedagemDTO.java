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
    private Integer adultos;
    private Integer criancas;
    private Long idHotel;
    private Integer quantidadeDeQuartos;
    private String nomeHotel;
    private String camaExtra;
    private String itemExtra;
    private Long idQuarto;
    private String numeroQuarto;
    private Long idCamasExtrasNaReserva;
    private Long idItemUsadoNaHospedagem;

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
        if (hospedagem.getItemUsadoNaHospedagem() != null && hospedagem.getItemUsadoNaHospedagem().getItem() != null) {
            dto.itemExtra = hospedagem.getItemUsadoNaHospedagem().getItem().getNome();
        }
        if (hospedagem.getCamasExtrasNaReserva() != null) {
        
            dto.idCamasExtrasNaReserva = hospedagem.getCamasExtrasNaReserva().getId();
        }
        if (hospedagem.getItemUsadoNaHospedagem() != null) {
            dto.idItemUsadoNaHospedagem = hospedagem.getItemUsadoNaHospedagem().getId();
        }

        return dto;
    }
}
