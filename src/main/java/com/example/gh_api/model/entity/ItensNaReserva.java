package com.example.gh_api.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItensNaReserva {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private long id;

    @ManyToOne
    private Reserva reserva;
    @ManyToOne
    private ItemNoHotel itemNoHotel;
    private int quantidade;
}
