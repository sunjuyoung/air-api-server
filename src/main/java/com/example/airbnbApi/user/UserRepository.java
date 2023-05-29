package com.example.airbnbApi.user;

import com.example.airbnbApi.listing.Listing;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<Account,Integer>,UserRepositoryExtension{

    @EntityGraph(attributePaths = {"favorites"})
    Optional<Account> findByEmail(String email);

    Optional<Account> findAccountByEmail(String email);

    boolean existsByName(String name);

    boolean existsByEmail(String email);

    @EntityGraph(attributePaths = {"favorites"})
    Account findFavoritesById(Integer id);


    @Query("select a from Account a where a.id = :account_id")
    Account findOnlyId(@Param("account_id") int account_id);




}
