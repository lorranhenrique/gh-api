package com.example.gh_api.model.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Data
@NoArgsConstructor
public class TipoCamaNoQuarto {

    private int quantidade;

    @ManyToOne
    private TipoDeCama tipoDeCama;
    @ManyToOne
    private TipoDeQuarto tipoDeQuarto;
}
