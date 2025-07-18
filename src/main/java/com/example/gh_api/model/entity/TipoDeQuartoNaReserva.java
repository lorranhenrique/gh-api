package com.example.gh_api.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"reserva", "tipoDeQuarto"})
public class TipoDeQuartoNaReserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Reserva reserva;
    @ManyToOne
    private TipoDeQuarto tipoDeQuarto;
    private int quantidade;
    
    public TipoDeQuartoNaReserva(Reserva reserva, TipoDeQuarto tipoDeQuarto, int quantidade) {
        this.reserva = reserva;
        this.tipoDeQuarto = tipoDeQuarto;
        this.quantidade = quantidade;
    }
}
