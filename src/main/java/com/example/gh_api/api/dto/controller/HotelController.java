package com.example.gh_api.api.dto.controller;

import com.example.gh_api.api.dto.HotelDTO;

import com.example.gh_api.exception.RegraNegocioException;
import com.example.gh_api.model.entity.Hotel;
import com.example.gh_api.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
@CrossOrigin
public class HotelController {
    private final HotelService service;

    public Hotel convert(HotelDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Hotel hotel = modelMapper.map(dto, Hotel.class);
        return hotel;
    }
}
