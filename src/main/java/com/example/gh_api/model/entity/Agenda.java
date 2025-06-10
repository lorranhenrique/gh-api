package com.example.gh_api.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Agenda {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private long id;
    
    @ManyToOne
    private ServicoNoHotel servico;
    private String horarioInicio;
    private String horarioFim;
    private String dias;

}
