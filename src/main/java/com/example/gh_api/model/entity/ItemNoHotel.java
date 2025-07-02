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
<<<<<<< HEAD
    private long id;
    
=======
    private Long id;
    ;
>>>>>>> e3456e3a4ad71634ea65d705644442acfd1de9a7
    private Integer estoque;
    private Float preco;
    @ManyToOne
    private Hotel hotel;
    @ManyToOne
    private Item item;
}
