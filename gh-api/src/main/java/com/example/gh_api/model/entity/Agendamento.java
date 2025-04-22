package com.example.gh_api.model.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Hospedagem hospedagem;
    @ManyToOne
    private Agenda agenda;
    @ManyToOne
    private Reserva reserva;
    @OneToOne
    private Trabalhador trabalhador;
    private String horarioInicio;
}
