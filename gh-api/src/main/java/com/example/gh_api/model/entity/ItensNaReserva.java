package com.example.gh_api.model.entity;


import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Data
@NoArgsConstructor
public class ItensNaReserva {

    @ManyToOne
    private Reserva reserva;
    @OneToMany
    private Item item;
}
