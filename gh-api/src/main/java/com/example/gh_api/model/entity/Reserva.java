package com.example.gh_api.model.entity;

import javax.persistence.*;
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

    @ManyToOne
    private Hotel hotel;
    @ManyToOne
    private Hospede hospede;
    @OneToOne (optional = true)
    private Hospedagem hospedagem;
    private String dataChegada;
    private String dataSaida;
}
