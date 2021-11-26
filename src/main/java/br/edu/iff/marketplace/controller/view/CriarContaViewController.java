/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.iff.marketplace.controller.view;

import br.edu.iff.marketplace.model.Usuario;
import br.edu.iff.marketplace.model.Vendedor;
import br.edu.iff.marketplace.service.UsuarioService;
import br.edu.iff.marketplace.service.VendedorService;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path="/criarconta")
public class CriarContaViewController {
    
    @Autowired
    private VendedorService vservice;
    
    @Autowired
    private UsuarioService uservice;
    
    @GetMapping
    public String create(Model model){
        
        return "criarconta";
    }
    
    @GetMapping(path="/usuario")
    public String cadastroUsuario(Model model){
        model.addAttribute("usuario", new Usuario());
        return "formUsuario";
    }
    @PostMapping(path="/usuario")
    public String salvarUsuario(@Valid @ModelAttribute Usuario usuario, BindingResult result, Model model){
        if(result.hasErrors()){
            
            model.addAttribute("msgErros", result.getAllErrors());
            return "formUsuario";
        }
        
        List<FieldError> list = new ArrayList<>();
        
        for (FieldError fe : result.getFieldErrors()) {
            if (!fe.getField().equals("dataDeNascimento")) {
                list.add(fe);
            }
        }
        if (!list.isEmpty()) {
            model.addAttribute("msgErros", list);
            return "formUsuario";
        }
        
        usuario.setId(null);
        try{
            uservice.save(usuario);
            model.addAttribute("msgSucesso", "Usuario cadastrado com sucesso!");
            model.addAttribute("usuario", new Usuario());
            return "formUsuario";
            
        }catch(Exception e){
            model.addAttribute("msgErros", new ObjectError("usuario", e.getMessage()));
            return "formUsuario";
        }
    }
    
    
    @GetMapping(path="/vendedor")
    public String cadastroVendedor(Model model){
        model.addAttribute("vendedor", new Vendedor());
        return "formVendedor";
    }
    
    @PostMapping(path="/vendedor")
    public String salvarVendedor(@Valid @ModelAttribute Vendedor vendedor, BindingResult result, Model model){
        if(result.hasErrors()){
            
            model.addAttribute("msgErros", result.getAllErrors());
            return "formVendedor";
        }
        
        List<FieldError> list = new ArrayList<>();
        
        for (FieldError fe : result.getFieldErrors()) {
            if (!fe.getField().equals("dataDeNascimento")) {
                list.add(fe);
            }
        }
        if (!list.isEmpty()) {
            model.addAttribute("msgErros", list);
            return "formVendedor";
        }
        
        vendedor.setId(null);
        try{
            vservice.save(vendedor);
            model.addAttribute("msgSucesso", "Vendedor cadastrado com sucesso!");
            model.addAttribute("vendedor", new Vendedor());
            return "formVendedor";
            
        }catch(Exception e){
            model.addAttribute("msgErros", new ObjectError("vendedor", e.getMessage()));
            return "formVendedor";
        }
    }
    
    
    
    
}
