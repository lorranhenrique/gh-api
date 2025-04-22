package com.example.gh_api.model.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Data
@NoArgsConstructor
public class CamasExtrasNaReserva {

    @ManyToOne
    private Reserva reserva;
    @ManyToMany
    private CamaNoHotel camaNoHotel;
    private int quantidade;
}
