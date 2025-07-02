package com.example.gh_api.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoDeQuarto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo;
    private Integer quantidadeTotal;
    private Float preco;
    private Integer quantidadeCamas; // Array<Long, Integer> quantidadeCamas;
    private Float tarifaBalcao;
    private String imagem;
}
