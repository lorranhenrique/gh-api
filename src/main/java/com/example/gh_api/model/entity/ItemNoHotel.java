package com.example.gh_api.model.entity;


import jakarta.persistence.*;
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
    private Long id;
    ;
    private Integer estoque;
    private Float preco;
    @ManyToOne
    private Hotel hotel;
    @ManyToOne
    private Item item;
}
