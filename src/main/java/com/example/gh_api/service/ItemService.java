package com.example.gh_api.service;

import com.example.gh_api.exception.RegraNegocioException;
import com.example.gh_api.model.entity.Item;
import com.example.gh_api.model.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ItemService {

    private ItemRepository repository;

    public ItemService(ItemRepository repository) { this.repository = repository; }

    public List<Item> getAllItems() { return repository.findAll(); }

    public Optional<Item> getItemById(Long id) { return repository.findById(id); }

    @Transactional
    public Item save(Item item) {
        validate(item);
        return repository.save(item);
    }

    @Transactional
    public void delete(Item item) {
        Objects.requireNonNull(item.getId());
        repository.delete(item);
    }

    public void validate(Item item){
        ArrayList<String> missingFields = new ArrayList<>();

        if (item.getNome() == null || item.getNome().trim().equals("")) {
            missingFields.add("nome");
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
