package com.soa.library.repository;

import com.soa.library.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    long count();
    @Query("SELECT SUM(s.totalPrices) FROM ShoppingCart s")
    Double getTotalPrice();
}
