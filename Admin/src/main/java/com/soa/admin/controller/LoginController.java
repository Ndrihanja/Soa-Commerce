package com.soa.admin.controller;

import com.soa.library.dto.AdminDto;
import com.soa.library.model.Admin;
import com.soa.library.model.Customer;
import com.soa.library.service.ShoppingCartService;
import com.soa.library.service.impl.AdminServiceImpl;
import com.soa.library.service.impl.CustomerServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
    @Autowired
    private AdminServiceImpl adminService;
    @Autowired
    private CustomerServiceImpl customerService;
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("title", "Login");
        return "pages-login";
    }

    @RequestMapping(value = {"/index", "/"})
    public String home(Model model) {
        long countClient = customerService.count();
        long countSale = shoppingCartService.count();
        double totalPrices = shoppingCartService.getTotalPrice();
        model.addAttribute("title", "Home Page");
        model.addAttribute("countClient", countClient);
        model.addAttribute("countSale", countSale);
        model.addAttribute("totalPrices", totalPrices);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "redirect:/login";
        }
        return "index";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("title", "Register");
        model.addAttribute("adminDto", new AdminDto());
        System.out.println(model.getAttribute("adminDto"));
        return "pages-register";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword(Model model) {
        model.addAttribute("title", "Mot de Passe Oublié");
        model.addAttribute("adminDto", new AdminDto());
        return "pages-register";
    }

    @PostMapping("/register-new")
    public String addNewAdmin(@Valid @ModelAttribute("adminDto")AdminDto adminDto,
                              BindingResult result,
                              Model model) {

        try {
            if(result.hasErrors()) {
                model.addAttribute("adminDto",adminDto);
                result.toString();
                return "pages-register";
            }

            String username = adminDto.getUsername();
            Admin admin = adminService.findByUsername(username);

            if(admin != null) {
                model.addAttribute("adminDto", adminDto);
                model.addAttribute("emailError", "Votre mail est déjà assigné à un compte!");
                return "pages-register";
            }
            if (adminDto.getPassword().equals(adminDto.getRepeatPassword())) {
                adminDto.setPassword(passwordEncoder.encode(adminDto.getPassword()));
                adminService.save(adminDto);
                model.addAttribute("success", "Enregistrement avec succès!");
                model.addAttribute("adminDto", adminDto);
            }else {
                model.addAttribute("adminDto", adminDto);
                model.addAttribute("passwordError", "Veuillez saisir un mot de passe identique!");
                return "pages-register";
            }



        }catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errors", "L'enregistrement n'est pas effectué avec succès! Erreur de serveur!");
        }

        return "pages-register";

    }
}
