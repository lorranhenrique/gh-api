package com.example.gh_api.model.entity;


import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private long id;

    private String nome;
    private String cep;
    private String estado;
    private String cidade;
    private String bairro;
    private String logradouro;
    private String numero;
    private String telefone;
    private String celular;
    private String email;
    private String imagem;
}
