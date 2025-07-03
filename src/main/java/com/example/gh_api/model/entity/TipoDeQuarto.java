package com.example.gh_api.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;
import java.util.HashMap;

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

    @ElementCollection
    private Map<TipoDeCama, Integer> quantidadeCamas = new HashMap<>();

    private Float tarifaBalcao;
    private String imagem;
}
