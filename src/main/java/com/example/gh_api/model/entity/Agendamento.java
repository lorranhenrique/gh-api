package com.example.gh_api.model.entity;

import jakarta.persistence.*;
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

    @ManyToOne
    private Hospedagem hospedagem;
    //@ManyToOne
    //private Agenda agenda;
    @ManyToOne
    private Trabalhador trabalhador;
    private String horarioInicio;
    private String data;
    @ManyToOne
    private Servico servico;
}
