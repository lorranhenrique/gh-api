package com.example.gh_api.model.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CamaNoHotel {

    @ManyToOne
    private Hotel hotel;
    @ManyToOne
    private TipoDeCama tipoDeCama;
    private int quantidade;
}
