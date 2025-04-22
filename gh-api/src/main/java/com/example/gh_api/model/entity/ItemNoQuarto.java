package com.example.gh_api.model.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemNoQuarto {

    @OneToMany
    private Item item;
    @ManyToMany
    private TipoDeQuarto tipoDeQuarto;
    private int quantidade;
}
