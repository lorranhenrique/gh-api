package com.example.gh_api.model.repository;

import com.example.gh_api.model.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

}
