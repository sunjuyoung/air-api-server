package com.example.airbnbApi.category;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CategoryRepository extends JpaRepository<Category,Integer> {

    @EntityGraph(attributePaths = {"listings"})
    List<Category> findAll();

    @EntityGraph(attributePaths = {"listings"})
    Category findByName(String name);


    @EntityGraph(attributePaths = {"listings"})
    Set<Category> findByNameIn(Set<String> name);


    @Query("select c from Category c where c.name =:name ")
    Optional<Category> findOnlyCategoryByName(@Param("name")String name);


}
