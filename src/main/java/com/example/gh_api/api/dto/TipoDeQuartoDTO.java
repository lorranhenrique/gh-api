package com.example.gh_api.api.dto;

import com.example.gh_api.model.entity.TipoDeCama;
import com.example.gh_api.model.entity.TipoDeQuarto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoDeQuartoDTO {

    private Long id;
    private String tipo;
    private Integer quantidadeTotal;
    private Float preco;
    private Map<TipoDeCama, Integer> quantidadeCamas = new HashMap<>();
    private Float tarifaBalcao;
    private String imagem;

    public static TipoDeQuartoDTO create(TipoDeQuarto tipoDeQuarto) {
        ModelMapper modelMapper = new ModelMapper();
        TipoDeQuartoDTO dto = modelMapper.map(tipoDeQuarto, TipoDeQuartoDTO.class);
        dto.tipo = tipoDeQuarto.getTipo();
        dto.quantidadeTotal = tipoDeQuarto.getQuantidadeTotal();
        dto.preco = tipoDeQuarto.getPreco();
        dto.quantidadeCamas = tipoDeQuarto.getQuantidadeCamas();
        dto.tarifaBalcao = tipoDeQuarto.getTarifaBalcao();
        dto.imagem = tipoDeQuarto.getImagem();
        return dto;
    }
}
