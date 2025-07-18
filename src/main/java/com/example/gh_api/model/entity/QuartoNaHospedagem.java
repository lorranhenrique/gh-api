package com.example.gh_api.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@EqualsAndHashCode(exclude = {"hospedagem", "quarto"})
public class QuartoNaHospedagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Hospedagem hospedagem;
    @ManyToOne
    private Quarto quarto;

    public QuartoNaHospedagem(Hospedagem hospedagem, Quarto quarto) {
        this.hospedagem = hospedagem;
        this.quarto = quarto;
    }
}
