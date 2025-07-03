package com.example.gh_api.api.dto;

import com.example.gh_api.model.entity.Cargo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CargoDTO {
    private Long id;
    private String nome;
    private String descricao;
    private Float salario;

    public static CargoDTO create(Cargo cargo) {
        ModelMapper modelMapper = new ModelMapper();
        CargoDTO dto = modelMapper.map(cargo, CargoDTO.class);
        dto.nome = cargo.getNome();
        dto.descricao = cargo.getDescricao();
        dto.salario = cargo.getSalario();
        return dto;
    }
}
