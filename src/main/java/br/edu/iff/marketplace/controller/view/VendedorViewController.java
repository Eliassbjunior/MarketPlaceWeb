/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.iff.marketplace.controller.view;


import br.edu.iff.marketplace.model.Vendedor;
import br.edu.iff.marketplace.service.VendedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author elias
 */
@Controller
@RequestMapping(path = "/vendedores")
public class VendedorViewController {


    @Autowired
    private VendedorService service;
    
    @GetMapping
    public String getAll(
            Model model, @RequestParam(name = "search", defaultValue = "", required=false ) String vendedor){
        
        if(vendedor.equalsIgnoreCase("")){
            model.addAttribute("vendedores", service.findAll());
        }else{
            model.addAttribute("vendedores", service.findByNome(vendedor));
        } 
        
        return "vendedores";
    
    }
    
    @GetMapping(path="/{id}")
    public String getProdutos(@PathVariable("id") Long id, Model model){
        
        Vendedor vendedor = new Vendedor();
        vendedor = service.findById(id) ;
        
        model.addAttribute("produtos", vendedor.getProdutos());
        
        return "loja";
        
    }
    
    @GetMapping(path="/deletar")
    public String deleteVendedor(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user = authentication.getName();
        Vendedor obj = service.findByUser(user);
        service.delete(obj.getId());
        return "redirect:/logout";
        
    }
    @GetMapping(path = "/{id}/deletar")
    public String deletar(@PathVariable("id") Long id) {

        Vendedor vendedor = service.findById(id);
        
       try{
           service.delete(id);
           return "redirect:/vendedores";
       }catch(Exception e){
           return "redirect:/";
       }
        
      
    }   
    
            
}

