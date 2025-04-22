package com.example.gh_api.model.entity;


import javax.persistence.*;
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
    private int id;

    private int quantidadeTotal;
    private float preco;
    private int quantidadeCamas;
    private float tarifaBalcao;
    private String imagem;
}
