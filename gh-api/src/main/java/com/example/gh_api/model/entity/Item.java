package com.example.gh_api.model.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Item {

    private int estoque;
    private float preco;
    private String imagem;
    @ManyToMany
    private Hotel hotel;
}
