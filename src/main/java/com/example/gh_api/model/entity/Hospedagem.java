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
    private int adultos;
    private int criancas;

    @OneToOne
    private Hospede hospede;
    @OneToOne
    private Hotel hotel;
    // COMPLETAR
}
