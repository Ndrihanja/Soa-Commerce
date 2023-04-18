package com.soa.client.controller;

import com.soa.library.dto.ProductDto;
import com.soa.library.model.Category;
import com.soa.library.model.Customer;
import com.soa.library.model.Product;
import com.soa.library.model.ShoppingCart;
import com.soa.library.service.CategoryService;
import com.soa.library.service.CustomerService;
import com.soa.library.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = {"/index", "/"}, method = RequestMethod.GET)
    public String home(Model model, Principal principal, HttpSession session){

        List<Product> products1 = productService.getAllProducts();
        List<Product> products = new ArrayList<>();
        for(int i = 0;i<4 && i< products1.size(); i++) {
            products.add(products1.get(i));
        }

        model.addAttribute("products", products);
        if(principal != null){
            session.setAttribute("username", principal.getName());
            Customer customer = customerService.findByUsername(principal.getName());
            ShoppingCart cart = customer.getShoppingCart();
            int total = 0;
            if (cart != null) {
                session.setAttribute("totalItems", cart.getTotalItems());
            }else {
                session.setAttribute("totalItems", total);
            }
        }else{
            session.removeAttribute("username");
        }
        return "home";
    }

    @GetMapping("/home")
    public String index(Model model){
        List<Category> categories = categoryService.findAll();
        List<ProductDto> productDtos = productService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("products", productDtos);
        return "index";
    }
}
