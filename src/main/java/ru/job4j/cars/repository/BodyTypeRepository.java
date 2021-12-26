package ru.job4j.cars.repository;

import ru.job4j.cars.model.BodyType;

import java.util.List;

public interface BodyTypeRepository {

    List<BodyType> findAll();
}
