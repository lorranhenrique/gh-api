package com.example.gh_api.api.dto;

import com.example.gh_api.model.entity.TipoDeCama;
import com.example.gh_api.model.entity.TipoDeQuarto;
import com.example.gh_api.model.entity.TipoCamaNoQuarto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoDeQuartoDTO {

    private Long id;
    private String tipo;
    private Integer quantidadeTotal;
    private Float preco;
    private Float tarifaBalcao;
    private String imagem;
    private List<String> camas;
    private List<TipoCamaNoQuartoDTO> tipoCamaNoQuarto; // para cadastro de tipo de quarto

    public static TipoDeQuartoDTO create(TipoDeQuarto tipoDeQuarto) {
        ModelMapper modelMapper = new ModelMapper();
        /*
         modelMapper.typeMap(TipoDeQuarto.class, TipoDeQuartoDTO.class)
                .addMappings(mapper -> mapper.skip(TipoDeQuartoDTO::setQuantidadeCamas));
        */
        TipoDeQuartoDTO dto = modelMapper.map(tipoDeQuarto, TipoDeQuartoDTO.class);
        dto.tipo = tipoDeQuarto.getTipo();
        dto.quantidadeTotal = tipoDeQuarto.getQuantidadeTotal();
        dto.preco = tipoDeQuarto.getPreco();

        dto.camas = tipoDeQuarto.getTipoCamaNoQuarto().stream()
                .map(nToN -> {
                String tipoDeCama = nToN.getTipoDeCama().getTipo();
                Integer quantidade = nToN.getQuantidade();
                return quantidade + " x " + tipoDeCama;
                })
                .collect(Collectors.toList());

        dto.tarifaBalcao = tipoDeQuarto.getTarifaBalcao();
        dto.imagem = tipoDeQuarto.getImagem();
        return dto;
    }
}
