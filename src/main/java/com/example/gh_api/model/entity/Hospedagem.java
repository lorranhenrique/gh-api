package com.example.gh_api.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "quartoNaHospedagem")
public class Hospedagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String checkIn;
    private String checkOut;
    private Integer adultos;
    private Integer criancas;
    private Boolean reserva;

    @ManyToOne
    private Hospede hospede;
    @ManyToOne
    private Hotel hotel;
    @ManyToOne
    private CamasExtrasNaReserva camasExtrasNaReserva;
    @ManyToOne
    private ItemUsadoNaHospedagem itemUsadoNaHospedagem;

    @JsonIgnore
    @OneToMany(mappedBy = "hospedagem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuartoNaHospedagem> quartoNaHospedagem;

    @JsonIgnore
    @OneToMany(mappedBy = "hospedagem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Agendamento> agendamentos;
}
