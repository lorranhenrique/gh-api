package com.example.gh_api.model.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Data
@NoArgsConstructor
public class TipoQuartoNaReserva {

    @ManyToOne
    private TipoDeQuarto tipoDeQuarto;
    @ManyToOne
    private Reserva reserva;
    private int quantidade;
}
