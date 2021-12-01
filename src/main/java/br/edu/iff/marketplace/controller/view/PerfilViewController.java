/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.iff.marketplace.controller.view;

import br.edu.iff.marketplace.model.Produto;
import br.edu.iff.marketplace.model.Usuario;
import br.edu.iff.marketplace.model.Vendedor;
import br.edu.iff.marketplace.service.ProdutoService;
import br.edu.iff.marketplace.service.UsuarioService;
import br.edu.iff.marketplace.service.VendedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    
    @Autowired
    private UsuarioService uservice;
    
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
    public String salvarProduto(@ModelAttribute Produto produto, BindingResult result, Model model){
        if(result.hasErrors()){
            
            model.addAttribute("msgErros", result.getAllErrors());
            return "formProduto";
        }
        if(produto.getId()!= null){
            service.update(produto);
            
            return "redirect:/produtos/meusprodutos";
        }else{
            produto.setId(null);
            Vendedor vendedor = new Vendedor();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
            String user = authentication.getName();
        
            vendedor = vservice.findByUser(user);
            produto.setVendedor(vendedor);
            
            try{
                service.save(produto);
                return "redirect:/produtos/meusprodutos";
            
            }catch(Exception e){
                model.addAttribute("msgErros", new ObjectError("produto", e.getMessage()));
                return "formProduto";
            }
        }
    }

    @GetMapping(path="/edit/usuario")
    public String editarUsuario(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user = authentication.getName();
        model.addAttribute("usuario", uservice.findByUser(user));
        
        return "formUsuarioEdit";
    }
    @PostMapping(path="/edit/usuario/{id}")
    public String updateVendedor(@ModelAttribute Usuario usuario, Model model){
        
        Usuario obj = uservice.findById(usuario.getId());
        usuario.setUser(obj.getUser());
        usuario.setCpf(obj.getCpf());
        usuario.setSenha(obj.getSenha());
        usuario.setPermissoes(obj.getPermissoes());
        try{
            uservice.update(usuario);
            return "redirect:/profile";
            
        }catch(Exception e){
            model.addAttribute("msgErros", new ObjectError("usuario", e.getMessage()));
            return "formUsuarioEdit";
        }
    }
    
    @GetMapping(path="/edit/vendedor")
    public String editarVendedor(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String user = authentication.getName();
        
        model.addAttribute("vendedor", vservice.findByUser(user));
        return "formVendedorEdit";
        
    }
    
    @PostMapping(path="/edit/vendedor/{id}")
    public String updateVendedor(@ModelAttribute Vendedor vendedor, Model model){
        
        Vendedor obj = vservice.findById(vendedor.getId());
        vendedor.setUser(obj.getUser());
        vendedor.setCnpj(obj.getCnpj());
        vendedor.setSenha(obj.getSenha());
        vendedor.setPermissoes(obj.getPermissoes());
        try{
            vservice.update(vendedor);
            return "redirect:/profile";
            
        }catch(Exception e){
            model.addAttribute("msgErros", new ObjectError("vendedor", e.getMessage()));
            return "formVendedorEdit";
        }
    }
    
}
