package com.soa.library.service;

import com.soa.library.model.Customer;
import com.soa.library.model.Product;
import com.soa.library.model.ShoppingCart;

public interface ShoppingCartService {
    ShoppingCart addItemToCart(Product product, int quantity, Customer customer);

    ShoppingCart updateItemInCart(Product product, int quantity, Customer customer);

    ShoppingCart deleteItemFromCart(Product product, Customer customer);

    long count();

    double getTotalPrice();
}
