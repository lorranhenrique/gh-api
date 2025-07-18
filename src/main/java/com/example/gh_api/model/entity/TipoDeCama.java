package com.example.gh_api.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "tipoCamaNoQuarto")
public class TipoDeCama {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo;
    private Integer quantidadeAdultos;
    private Integer quantidadeCriancas;

    @OneToMany(mappedBy = "tipoDeCama")
    @ToString.Exclude
    private Set<TipoCamaNoQuarto> tipoCamaNoQuarto;
}
