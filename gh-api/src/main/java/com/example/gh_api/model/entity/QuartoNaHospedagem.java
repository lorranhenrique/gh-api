package com.example.gh_api.model.entity;


import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Data
@NoArgsConstructor
public class QuartoNaHospedagem {

    @ManyToOne
    private Hospedagem hospedagem;
    @OneToMany
    private Quarto quarto;
}
