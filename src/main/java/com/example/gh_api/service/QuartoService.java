package com.example.gh_api.service;

import com.example.gh_api.exception.RegraNegocioException;
import com.example.gh_api.model.entity.Quarto;
import com.example.gh_api.model.repository.QuartoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class QuartoService {

    private QuartoRepository repository;

    public QuartoService(QuartoRepository repository) { this.repository = repository; }

    public List<Quarto> getAllQuartos() { return repository.findAll(); }

    public Optional<Quarto> getQuartoById(Long id) { return repository.findById(id); }

    @Transactional
    public Quarto save(Quarto quarto) {
        validate(quarto);
        return repository.save(quarto);
    }

    @Transactional
    public void delete(Quarto quarto) {
        Objects.requireNonNull(quarto.getId());
        repository.delete(quarto);
    }

    public void validate(Quarto quarto){
        ArrayList<String> missingFields = new ArrayList<>();

        if (quarto.getHotel() == null) {
            missingFields.add("hotel");
        }

        if (quarto.getTipoDeQuarto() == null) {
            missingFields.add("tipo de quarto");
        }

        if (quarto.getNumero() == null || quarto.getNumero().trim().equals("")) {
            missingFields.add("número");
        }

        if (quarto.getSituacao() == null || quarto.getSituacao().trim().equals("")) {
            missingFields.add("situação");
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
