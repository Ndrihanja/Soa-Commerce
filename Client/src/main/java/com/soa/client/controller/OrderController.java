package com.soa.client.controller;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.lowagie.text.pdf.PdfDocument;
import com.lowagie.text.pdf.PdfWriter;
import com.soa.library.model.Customer;
import com.soa.library.model.ShoppingCart;
import com.soa.library.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.xhtmlrenderer.pdf.ITextRenderer;


import java.security.Principal;

@Controller
public class OrderController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private TemplateEngine templateEngine;

    @GetMapping("/check-out")
    public String checkout(Model model, Principal principal){
        if(principal == null){
            return "redirect:/login";
        }
        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);
        if(customer.getPhoneNumber().trim().isEmpty() || customer.getAddress().trim().isEmpty()
                || customer.getCity().trim().isEmpty() || customer.getCountry().trim().isEmpty()){

            model.addAttribute("customer", customer);
            model.addAttribute("error", "You must fill the information after checkout!");
            return "account";
        }else{
            model.addAttribute("customer", customer);
            ShoppingCart cart = customer.getShoppingCart();
            model.addAttribute("cart", cart);
        }
        return "checkout";
    }


//    @GetMapping("/order")
//    public String order(){
//
//        return "order";
//    }

    @GetMapping("/order")
    @ResponseBody
    public ResponseEntity<byte[]> genererFacturePDF(Model model, Principal principal) throws Exception {
        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);
        model.addAttribute("customer", customer);
        ShoppingCart cart = customer.getShoppingCart();
        model.addAttribute("cart", cart);

        Context context = new Context();

        context.setVariables(model.asMap());
        // Génération de la page HTML de la facture à partir du modèle
        String html = templateEngine.process("facture",context);
        // Génération du PDF à partir de la page HTML
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream);
        renderer.finishPDF();

        // Écrire le contenu PDF généré dans un fichier "output.pdf"
        FileOutputStream fos = new FileOutputStream("output.pdf");
        fos.write(outputStream.toByteArray());
        fos.close();

        // Créer une réponse HTTP avec le contenu PDF en tant que corps de la réponse
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=output.pdf");
        headers.setContentLength(outputStream.size());
        return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
    }
}
