package com.example.gh_api.service;

import com.example.gh_api.exception.RegraNegocioException;
import com.example.gh_api.model.entity.TipoDeQuarto;
import com.example.gh_api.model.repository.TipoDeQuartoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TipoDeQuartoService {

    private TipoDeQuartoRepository repository;

    public TipoDeQuartoService(TipoDeQuartoRepository repository) { this.repository = repository; }

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

        if (tipoDeQuarto.getQuantidadeCamas() == null || tipoDeQuarto.getQuantidadeCamas() < 0) {
            missingFields.add("quantidade camas");
        }

        if (tipoDeQuarto.getTarifaBalcao() == null || tipoDeQuarto.getTarifaBalcao() < 0) {
            missingFields.add("tarifa balcão");
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
