package com.example.gh_api.service;

import com.example.gh_api.model.entity.Item;
import com.example.gh_api.model.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ItemService {
    private ItemRepository repository;

    public ItemService(ItemRepository repository) {
        this.repository = repository;
    }

    public List<Item> getAllItems() {
        return repository.findAll();
    }

    public Optional<Item> getItemById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Item save(Item item) {
        validate(item);
        return repository.save(item);
    }

    @Transactional
    public void delete(Item item){
        Objects.requireNonNull(item.getId());
        repository.delete(item);
    }

    public void validate(Item item) {

    }
}
