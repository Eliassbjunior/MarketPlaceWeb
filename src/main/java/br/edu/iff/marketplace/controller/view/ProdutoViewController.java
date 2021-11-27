/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.iff.marketplace.controller.view;

import br.edu.iff.marketplace.model.Vendedor;
import br.edu.iff.marketplace.service.ProdutoService;
import br.edu.iff.marketplace.service.VendedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/produtos")
public class ProdutoViewController {
    @Autowired
    private ProdutoService service;
    
    @Autowired
    private VendedorService vservice;
    
    @GetMapping
    public String getAll(
            Model model, @RequestParam(name = "search", defaultValue = "", required=false ) String produto
            ){
        
        if(produto.equalsIgnoreCase("")){
            model.addAttribute("produtos", service.findAll());
        }else{
            model.addAttribute("produtos", service.findByNome(produto));
        } 
        
        return "produtos";
    }

    @GetMapping(path="/meusprodutos")
    public String getProdutos(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        String user = authentication.getName();
        Vendedor vendedor = new Vendedor();
        vendedor = vservice.findByUser(user);
        
        model.addAttribute("produtos", vendedor.getProdutos());
        
        return "produtos";
        
    }
            
}