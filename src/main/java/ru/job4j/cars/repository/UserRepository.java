package ru.job4j.cars.repository;

import ru.job4j.cars.model.User;

public interface UserRepository {

    User createUser(User user);

    User findUserByEmail(String email);
}
