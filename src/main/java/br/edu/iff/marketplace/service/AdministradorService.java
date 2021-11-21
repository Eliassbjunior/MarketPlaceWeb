/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.iff.marketplace.service;

import br.edu.iff.marketplace.model.Administrador;
import br.edu.iff.marketplace.repository.AdministradorRepository;

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
public class AdministradorService {
    @Autowired
    private AdministradorRepository repo;
    
    public List<Administrador>findAll(int page, int size){
        Pageable p = PageRequest.of(page, size);
        return repo.findAll(p).toList();
            
    }
    
    public List<Administrador> findAll(){
        return repo.findAll();
    }
    
    public Administrador findById(Long id){
        Optional<Administrador> result = repo.findById(id);
        if(result.isEmpty()){
            throw new RuntimeException("Administrador não encontrado.");
        }
        
        return result.get();
    }
    
    public Administrador save(Administrador a){
       try{
           return repo.save(a);
       }catch(Exception e){
           throw new RuntimeException("Falha ao salvar o administrador.");
       }
        
    }
    
    public Administrador update(Administrador a, String senhaAtual, String novaSenha, String confirmarNovaSenha){
        Administrador obj = findById(a.getId());
        
        alterarSenha(obj,senhaAtual,novaSenha,confirmarNovaSenha);
       try{
           a.setCpf(obj.getCpf());
           return repo.save(a);
        }catch(Exception e){
            throw new RuntimeException("Falha ao atualizar o administrador.");
        }
        
    }
    
    public void delete(Long id){
        Administrador obj = findById(id);
        try{
           repo.delete(obj);
        }catch(Exception e){
           throw new RuntimeException("Falha ao excluir o administrador.");
        }
    }
    
    private void alterarSenha(Administrador obj, String senhaAtual, String novaSenha, String confirmarNovaSenha){
        if(!senhaAtual.isBlank() && !novaSenha.isBlank() && !confirmarNovaSenha.isBlank()){
            if(!senhaAtual.equals(obj.getSenha())){
                throw new RuntimeException("Senha Atual está incorreta.");
            }
            if(!novaSenha.equals(confirmarNovaSenha)){
                throw new RuntimeException("Nova senha e a senha de confirmação são diferentes.");
            }
            obj.setSenha(novaSenha);
        }
    }

    private void verificaUser(String user) {
        List<Administrador> result = repo.findByUser(user);
        if (!result.isEmpty()) {
            throw new RuntimeException("Username já cadastrado.");
        }
    }


}
