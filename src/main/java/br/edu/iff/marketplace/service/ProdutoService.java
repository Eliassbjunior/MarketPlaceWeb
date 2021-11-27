/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.iff.marketplace.service;

import br.edu.iff.marketplace.exception.NotFoundException;
import br.edu.iff.marketplace.model.Administrador;
import br.edu.iff.marketplace.model.Produto;
import br.edu.iff.marketplace.repository.ProdutoRepository;
import java.util.List;
import java.util.Optional;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author elias
 */
@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository repo;
    
    public List<Produto>findAll(int page, int size){
        Pageable p = PageRequest.of(page, size);
        return repo.findAll(p).toList();
            
    }
    
    public List<Produto> findAll(){
        return repo.findAll();
    }
    public List<Produto> findByNome(String nome){
        List<Produto> result = repo.findByNome('%'+nome+'%');
        
        if(result.isEmpty()){
            throw new NotFoundException("Produto não encontrado.");
        }
        return result;
    }
    public Produto findById(Long id){
        Optional<Produto> result = repo.findById(id);
        if(result.isEmpty()){
            throw new NotFoundException("Produto não encontrado.");
        }
        
        return result.get();
    }
    
    public List<Produto> findByProduto(String user){
        List<Produto> result = repo.findByProdutos(user);
        
        if(result.isEmpty()){
            throw new NotFoundException("Produto não encontrado.");
        }
        
        return result;
    }
    public Produto save(Produto a){
       try{
           return repo.save(a);
       }catch(Exception e){
           throw new RuntimeException("Falha ao salvar o Produto.");
       }
        
    }
     
     public Produto update(Produto a){
       Produto obj = findById(a.getId());
        
       try{
           a.setVendedor(obj.getVendedor());
           return repo.save(a);
        }catch(Exception e){
            Throwable t = e;
            while(t.getCause() != null){
                t = t.getCause();
                if(t instanceof ConstraintViolationException){
                    throw ((ConstraintViolationException) t);
                }
            }
            throw new RuntimeException("Falha ao atualizar o Produto.");
        }
        
    }
    
    public void delete(Long id){
        Produto obj = findById(id);
        try{
           repo.delete(obj);
        }catch(Exception e){
           throw new RuntimeException("Falha ao excluir o Produto.");
        }
    }
}
