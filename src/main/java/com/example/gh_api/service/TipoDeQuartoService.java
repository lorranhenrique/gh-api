package com.example.gh_api.service;

import com.example.gh_api.api.dto.TipoDeQuartoDTO;
import com.example.gh_api.exception.RegraNegocioException;
import com.example.gh_api.model.entity.TipoDeQuarto;
import com.example.gh_api.model.repository.TipoDeCamaRepository;
import com.example.gh_api.model.repository.TipoDeQuartoRepository;
import com.example.gh_api.model.entity.TipoDeCama;
import com.example.gh_api.model.entity.TipoCamaNoQuarto;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

@Service
public class TipoDeQuartoService {

    private TipoDeQuartoRepository repository;

    private TipoDeCamaRepository tipoDeCamaRepository;

    public TipoDeQuartoService(TipoDeQuartoRepository repository, TipoDeCamaRepository tipoDeCamaRepository) { 
        this.repository = repository;
        this.tipoDeCamaRepository = tipoDeCamaRepository;
    }

    public List<TipoDeQuarto> getAllTipoDeQuartos() { return repository.findAll(); }

    public Optional<TipoDeQuarto> getTipoDeQuartoById(Long id) { return repository.findById(id); }

    @Transactional
    public TipoDeQuarto save(TipoDeQuarto tipoDeQuarto) {
        validate(tipoDeQuarto);
        return repository.save(tipoDeQuarto);
    }

    @Transactional
    public void delete(TipoDeQuarto tipoDeQuarto) {
        Objects.requireNonNull(tipoDeQuarto.getId());
        repository.delete(tipoDeQuarto);
    }

    public void validate(TipoDeQuarto tipoDeQuarto){
        ArrayList<String> missingFields = new ArrayList<>();

        if (tipoDeQuarto.getTipo() == null || tipoDeQuarto.getTipo().trim().equals("")) {
            missingFields.add("tipo");
        }

        if (tipoDeQuarto.getQuantidadeTotal() == null || tipoDeQuarto.getQuantidadeTotal() < 0) {
            missingFields.add("quantidade total");
        }

        if (tipoDeQuarto.getPreco() == null || tipoDeQuarto.getPreco() < 0) {
            missingFields.add("preço");
        }

        if (tipoDeQuarto.getTarifaBalcao() == null || tipoDeQuarto.getTarifaBalcao() < 0) {
            missingFields.add("tarifa balcão");
        }

        if (tipoDeQuarto.getTipoCamaNoQuarto() == null || tipoDeQuarto.getTipoCamaNoQuarto().isEmpty()) {
            missingFields.add("camas");
        } else {
            for (TipoCamaNoQuarto tipoCamaNoQuarto : tipoDeQuarto.getTipoCamaNoQuarto()) {
                TipoDeCama tipoDeCama = tipoCamaNoQuarto.getTipoDeCama();
                Integer quantidade = tipoCamaNoQuarto.getQuantidade();

                if (tipoDeCama == null) {
                    missingFields.add("tipo de cama");
                }
                if (quantidade == null || quantidade <= 0) {
                    missingFields.add("quantidade de camas do tipo " + tipoDeCama.getTipo());
                }
            }
        }
        if (missingFields.size() > 0) {
            if (missingFields.size() == 1){
                throw new RegraNegocioException("Por favor, preencha o seguinte campo: " + missingFields.get(0) + ".");
            }
            else {
                throw new RegraNegocioException("Por favor, preencha os seguinte campos: " + String.join(", ", missingFields) + ".");
            }
        }
    }

}
