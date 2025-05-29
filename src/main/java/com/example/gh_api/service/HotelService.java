package com.example.gh_api.service;

import com.example.gh_api.exception.RegraNegocioException;
import com.example.gh_api.model.entity.Hotel;
import com.example.gh_api.model.repository.HotelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class HotelService {
    private HotelRepository repository;

    public HotelService(HotelRepository repository) {
        this.repository = repository;
    }

    public List<Hotel> getAllHotels() {
        return repository.findAll();
    }

    public Optional<Hotel> getHotelById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Hotel save(Hotel hotel) {
        validate(hotel);
        return repository.save(hotel);
    }

    @Transactional
    public void delete(Hotel hotel) {
        Objects.requireNonNull(hotel.getId());
        repository.delete(hotel);
    }

    public void validate (Hotel hotel) {
        ArrayList<String> missingFields = new ArrayList<>();
        if (hotel.getNome() == null || hotel.getNome().trim().equals("")) {
            missingFields.add("nome");
        }

        if (hotel.getEstado() == null || hotel.getEstado().trim().equals("")) {
            missingFields.add("estado");
        }

        if (hotel.getCidade() == null || hotel.getCidade().trim().equals("")) {
            missingFields.add("cidade");
        }

        if (hotel.getCep() == null || hotel.getCep().trim().equals("")) {
            missingFields.add("CEP");
        }

        if (hotel.getBairro() == null || hotel.getBairro().trim().equals("")) {
            missingFields.add("bairro");
        }

        if (hotel.getLogradouro() == null || hotel.getLogradouro().trim().equals("")) {
            missingFields.add("logradouro");
        }

        if (hotel.getNumero() == null || hotel.getNumero().trim().equals("")) {
            missingFields.add("número");
        }

        if (hotel.getTelefone() == null || hotel.getTelefone().trim().equals("")) {
            missingFields.add("telefone");
        }

        if (hotel.getCelular() == null || hotel.getNumero().trim().equals("")) {
            missingFields.add("celular");
        }
        if (hotel.getEmail() == null || hotel.getEmail().trim().equals("")) {
            missingFields.add("e-mail");
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
