package com.example.gh_api.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trabalhador extends Pessoa {

    @ManyToOne
    private Hotel hotel;
    @ManyToOne
    private Cargo cargo;
}
