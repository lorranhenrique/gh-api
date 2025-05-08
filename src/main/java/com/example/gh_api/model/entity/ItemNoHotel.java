package com.example.gh_api.model.entity;


import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemNoHotel {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private long id;

    private int estoque;
    private float preco;
    @ManyToOne
    private Hotel hotel;
    @ManyToOne
    private Item item;
}
