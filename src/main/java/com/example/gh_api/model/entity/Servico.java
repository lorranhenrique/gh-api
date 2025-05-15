package com.example.gh_api.model.entity;


import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Servico {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private long id;

    private String nome;
    private String descricao;
    private Float preco;
    private String imagem;
    private Integer minutosPorAgendamento;
}