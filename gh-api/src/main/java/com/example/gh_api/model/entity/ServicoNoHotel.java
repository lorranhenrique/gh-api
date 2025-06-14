package com.example.gh_api.model.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicoNoHotel {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private long id;

    @ManyToOne
    private Hotel hotel;
    @OneToOne
    private Servico servico;
}
