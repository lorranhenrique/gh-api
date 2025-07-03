package com.example.gh_api.service;

import com.example.gh_api.exception.RegraNegocioException;
import com.example.gh_api.model.entity.ItemNoHotel;
import com.example.gh_api.model.repository.ItemNoHotelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ItemNoHotelService {
    private ItemNoHotelRepository repository;

    public ItemNoHotelService(ItemNoHotelRepository repository) {
        this.repository = repository;
    }

    public List<ItemNoHotel> getAllItems() {
        return repository.findAll();
    }

    public Optional<ItemNoHotel> getItemById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public ItemNoHotel save(ItemNoHotel item) {
        validate(item);
        return repository.save(item);
    }

    @Transactional
    public void delete(ItemNoHotel item){
        Objects.requireNonNull(item.getId());
        repository.delete(item);
    }

    public void validate(ItemNoHotel item) {
        ArrayList<String> missingFields = new ArrayList<>();
        if (item.getItem().getNome() == null || item.getItem().getNome().trim().equals("")) {
            missingFields.add("nome");
        }

        if (item.getEstoque() == null || item.getEstoque() < 0) {
            missingFields.add("estoque");
        }

        if (item.getPreco() == null || item.getPreco() < 0) {
            missingFields.add("preço");
        }

        if (item.getHotel() == null || item.getItem().getNome().trim().equals("")) {
            missingFields.add("hotel");
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
