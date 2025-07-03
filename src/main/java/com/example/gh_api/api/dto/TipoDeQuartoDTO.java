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
    private Map<Long, Integer> quantidadeCamas = new HashMap<>();
    private Float tarifaBalcao;
    private String imagem;

    public static TipoDeQuartoDTO create(TipoDeQuarto tipoDeQuarto) {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.typeMap(TipoDeQuarto.class, TipoDeQuartoDTO.class)
                .addMappings(mapper -> mapper.skip(TipoDeQuartoDTO::setQuantidadeCamas));

        TipoDeQuartoDTO dto = modelMapper.map(tipoDeQuarto, TipoDeQuartoDTO.class);
        dto.tipo = tipoDeQuarto.getTipo();
        dto.quantidadeTotal = tipoDeQuarto.getQuantidadeTotal();
        dto.preco = tipoDeQuarto.getPreco();

        Map<Long, Integer> camasParaDTO = new HashMap<>();
        Map<TipoDeCama, Integer> quantidadeCamas = tipoDeQuarto.getQuantidadeCamas();

        if (quantidadeCamas != null) {
            for (Map.Entry<TipoDeCama, Integer> entry : quantidadeCamas.entrySet()) {
                Long idCama = entry.getKey().getId();
                Integer quantidade = entry.getValue();
                camasParaDTO.put(idCama, quantidade);
            }
        }
        dto.quantidadeCamas = camasParaDTO;

        dto.tarifaBalcao = tipoDeQuarto.getTarifaBalcao();
        dto.imagem = tipoDeQuarto.getImagem();
        return dto;
    }
}
