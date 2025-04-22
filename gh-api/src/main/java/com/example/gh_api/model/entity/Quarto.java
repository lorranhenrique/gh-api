package com.example.gh_api.model.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quarto {

    @ManyToOne
    private Hotel hotel;
    @ManyToOne
    private TipoDeQuarto tipoDeQuarto;
    private String numero;
}
