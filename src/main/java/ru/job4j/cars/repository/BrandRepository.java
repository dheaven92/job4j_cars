package ru.job4j.cars.repository;

import ru.job4j.cars.model.Brand;

import java.util.List;

public interface BrandRepository {
    
    List<Brand> findAll();
}
