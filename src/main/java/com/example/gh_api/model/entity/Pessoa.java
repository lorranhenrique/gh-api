package com.example.gh_api.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pessoa {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    private String nome;
    private String telefone;
    private String email;
    private String estado;
    private String cidade;
    private String cep;
    private String bairro;
    private String logradouro;
    private String numeroMoradia;
    private String complemento;
    private String genero;
    private String senha;
    private String dataNascimento;
    private String cpf;
}
