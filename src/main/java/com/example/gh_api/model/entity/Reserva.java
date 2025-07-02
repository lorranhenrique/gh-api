package com.example.gh_api.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private Hospedagem hospedagem; // acho que vai roda
    @ManyToOne
    private TipoDeQuarto tipoDeQuarto;
}
