/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.iff.marketplace.controller.view;

import br.edu.iff.marketplace.model.Produto;
import br.edu.iff.marketplace.model.Vendedor;
import br.edu.iff.marketplace.service.ProdutoService;
import br.edu.iff.marketplace.service.VendedorService;
import java.util.List;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/profile")
public class PerfilViewController {
    
    @Autowired
    private ProdutoService service;
    
    @Autowired
    private VendedorService vservice;
    
    @GetMapping
    public String noprofile(Model model){
        model.addAttribute("noprofile");
        return "profile";
    }
    
    @GetMapping(path="/addproduto")
    public String cadastroProduto(Model model){
        
        model.addAttribute("produto", new Produto());
        return "formProduto";
    }
    
    @PostMapping(path="/addproduto")
    public String salvarProduto( @ModelAttribute Produto produto, BindingResult result, Model model){
        if(result.hasErrors()){
            
            model.addAttribute("msgErros", result.getAllErrors());
            return "formProduto";
        }
        
        produto.setId(null);
        
        
        Vendedor vendedor = new Vendedor();
        vendedor = vservice.findByUser("Ertiu123");
        produto.setVendedor(vendedor);
        produto.setPreco(1.000);
        try{
            service.save(produto);
            model.addAttribute("msgSucesso", "Produto cadastrado com sucesso!");
            
            model.addAttribute("produto", new Produto());
            return "formProduto";
            
        }catch(Exception e){
            model.addAttribute("msgErros", new ObjectError("produto", e.getMessage()));
            return "formProduto";
        }
    }
    
}
