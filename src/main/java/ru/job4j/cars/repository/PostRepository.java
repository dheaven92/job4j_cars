package ru.job4j.cars.repository;

import ru.job4j.cars.model.Post;

import java.util.List;

public interface PostRepository {

    List<Post> findAllWithCarAndUserAndOrderByCreated();

    List<Post> findAllCreateInLastDay();

    List<Post> findAllByCarBrandId(int brandId);

    List<Post> findAllByCarBodyTypeId(int bodyTypeId);

    Post savePost(Post post);

    Post findById(int id);

    void delete(Post post);
}
