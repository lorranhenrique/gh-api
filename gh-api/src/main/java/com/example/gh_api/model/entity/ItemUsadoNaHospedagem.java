package com.example.gh_api.model.entity;


import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemUsadoNaHospedagem {

    @ManyToOne
    private Hospedagem hospedagem;
    @ManyToMany
    private Item item;
    private int quantidade;
}
