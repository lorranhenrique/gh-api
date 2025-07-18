package com.example.gh_api.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

import com.example.gh_api.model.entity.TipoDeQuartoNaReserva;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "tipoDeQuartoNaReserva")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dataChegada;
    private String dataSaida;

    @ManyToOne
    private Hotel hotel;
    @ManyToOne
    private Hospede hospede;
    @OneToOne (optional = true)
    private Hospedagem hospedagem;

    @OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TipoDeQuartoNaReserva> tipoDeQuartoNaReserva = new HashSet<>();
}
