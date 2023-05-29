package com.example.airbnbApi.category;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface CategoryRepository extends JpaRepository<Category,Integer> {

    @EntityGraph(attributePaths = {"listings"})
    List<Category> findAll();

    @EntityGraph(attributePaths = {"listings"})
    Category findByName(String name);


    @EntityGraph(attributePaths = {"listings"})
    Set<Category> findByNameIn(Set<String> name);


    Category findOnlyCategoryByName(String name);

}
