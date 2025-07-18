package com.example.gh_api.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(exclude = {"tipoDeQuartoNaReserva", "tipoCamaNoQuarto"})
public class TipoDeQuarto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo;
    private Integer quantidadeTotal;
    private Float preco;

    @OneToMany(mappedBy = "tipoDeQuarto")
    private Set<TipoDeQuartoNaReserva> tipoDeQuartoNaReserva;

    @OneToMany(mappedBy = "tipoDeQuarto", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<TipoCamaNoQuarto> tipoCamaNoQuarto = new HashSet<>();

    private Float tarifaBalcao;
    private String imagem;
}
