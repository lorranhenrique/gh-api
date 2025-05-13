package com.example.gh_api.model.entity.repository;

import com.example.gh_api.model.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
