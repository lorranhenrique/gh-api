package com.example.gh_api.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hospedagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String checkIn;
    private String checkOut;
    private Integer adultos;
    private Integer criancas;
    private Boolean reserva;
    private Integer quantidadeDeQuartos;

    @ManyToOne
    private Hospede hospede;
    @ManyToOne
    private Hotel hotel;
    @ManyToOne
    private Quarto quarto;
    @ManyToOne
    private CamasExtrasNaReserva camasExtrasNaReserva;
    @ManyToOne
    private ItensNaReserva itensNaReserva;
}
