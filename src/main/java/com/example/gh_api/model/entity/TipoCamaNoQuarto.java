package com.example.gh_api.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"tipoDeCama", "tipoDeQuarto"})
public class TipoCamaNoQuarto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantidade;

    @ManyToOne
    @ToString.Exclude
    private TipoDeCama tipoDeCama;
    
    @ManyToOne
    @ToString.Exclude
    private TipoDeQuarto tipoDeQuarto;

    public TipoCamaNoQuarto(TipoDeQuarto tipoDeQuarto, TipoDeCama tipoDeCama, Integer quantidade) {
        this.tipoDeCama = tipoDeCama;
        this.tipoDeQuarto = tipoDeQuarto;
        this.quantidade = quantidade;
    }
}
