/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.iff.marketplace.controller.view;

import br.edu.iff.marketplace.exception.NotFoundException;
import br.edu.iff.marketplace.model.Produto;
import br.edu.iff.marketplace.model.Usuario;
import br.edu.iff.marketplace.model.Venda;
import br.edu.iff.marketplace.model.Vendedor;
import br.edu.iff.marketplace.service.ProdutoService;
import br.edu.iff.marketplace.service.UsuarioService;
import br.edu.iff.marketplace.service.VendaService;
import br.edu.iff.marketplace.service.VendedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/produtos")
public class ProdutoViewController {
    @Autowired
    private ProdutoService service;
    
    @Autowired
    private VendedorService vservice;
    
    @Autowired
    private UsuarioService uservice;
    
    @Autowired
    private VendaService vendaservice;
    
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
        
        model.addAttribute("meusprodutos", vendedor.getProdutos());
        
        return "meusprodutos";
        
    }
    @GetMapping(path = "/{id}/deletar")
    public String deletar(@PathVariable("id") Long id) {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user = authentication.getName();
        
        
        
        Produto obj = service.findById(id);
        
        Vendedor vendedor = new Vendedor();
        vendedor = obj.getVendedor();
       
        
        if(user.equalsIgnoreCase(vendedor.getUser())){
            service.delete(id);
            return "redirect:/produtos/meusprodutos";
        }else{
            throw new NotFoundException("Não é o vendedor deste produto");
        }   
    }   
    
    @GetMapping(path = "/{id}/editar")
    public String editar(@PathVariable("id") Long id, Model model){
        
        model.addAttribute("produto",service.findById(id));
        return "editProduto";
    }
    
    @GetMapping(path = "/comprar/{id}")
    public String comprar(@PathVariable("id") Long id, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user = authentication.getName();
        
        Usuario usuario = uservice.findByUser(user);
        Produto obj = service.findById(id);
        
        Venda venda = new Venda();
        venda.setProduto(obj);
        venda.setUsuario(usuario);
        venda.setVendedor(obj.getVendedor());
        venda.setEndereco(usuario.getEndereco());
        
        try{
            vendaservice.save(venda);
            
            
            return "redirect:/";
        }catch(Exception e){
            
            return "redirect:/profile";
        }
    }
}