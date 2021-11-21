/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.iff.marketplace.service;

import br.edu.iff.marketplace.model.Venda;
import br.edu.iff.marketplace.repository.VendaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author elias
 */
@Service
public class VendaService {
    @Autowired
    private VendaRepository repo;
    
    public List<Venda>findAll(int page, int size){
        Pageable p = PageRequest.of(page, size);
        return repo.findAll(p).toList();
            
    }
    
    public List<Venda> findAll(){
        return repo.findAll();
    }
    
    public Venda findById(Long id){
        Optional<Venda> result = repo.findById(id);
        if(result.isEmpty()){
            throw new RuntimeException("Venda não encontrada.");
        }
        
        return result.get();
    }
    
    public Venda save(Venda a){
       try{
           return repo.save(a);
       }catch(Exception e){
           throw new RuntimeException("Falha ao salvar a Venda.");
       }
        
    }
    
    public void delete(Long id){
        Venda obj = findById(id);
        try{
           repo.delete(obj);
        }catch(Exception e){
           throw new RuntimeException("Falha ao excluir a Venda.");
        }
    }
    
}
