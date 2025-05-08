package com.example.gh_api.model.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Data
@NoArgsConstructor
public class TipoCamaNoQuarto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int quantidade;

    @ManyToOne
    private TipoDeCama tipoDeCama;
    @ManyToOne
    private TipoDeQuarto tipoDeQuarto;
}
