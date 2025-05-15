package com.example.gh_api.service;

import com.example.gh_api.exception.RegraNegocioException;
import com.example.gh_api.model.entity.Hotel;
import com.example.gh_api.model.repository.HotelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    }
}
