/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.iff.marketplace.service;

import br.edu.iff.marketplace.exception.NotFoundException;
import br.edu.iff.marketplace.model.Administrador;
import br.edu.iff.marketplace.model.Usuario;
import br.edu.iff.marketplace.model.Vendedor;
import br.edu.iff.marketplace.repository.AdministradorRepository;
import br.edu.iff.marketplace.repository.UsuarioRepository;
import br.edu.iff.marketplace.repository.VendedorRepository;
import java.util.List;
import java.util.Optional;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author elias
 */
@Service
public class VendedorService {
    
    @Autowired
    private VendedorRepository repo;
    
    @Autowired
    private UsuarioRepository repoo;
    
    @Autowired
    private AdministradorRepository repooo;
    
    public List<Vendedor>findAll(int page, int size){
        Pageable p = PageRequest.of(page, size);
        return repo.findAll(p).toList();
            
    }
    
    public List<Vendedor> findAll(){
        return repo.findAll();
    }
    
    public Vendedor findById(Long id){
        Optional<Vendedor> result = repo.findById(id);
        if(result.isEmpty()){
            throw new NotFoundException("Vendedor não encontrado.");
        }
        
        return result.get();
    }
    
    public List<Vendedor> findByNome(String user){
        String nome = '%'+user+'%';
        List<Vendedor> result = repo.findByNome(nome,user);
        
        if(result.isEmpty()){
            throw new NotFoundException("Vendedor não encontrado.");
        }
        
        return result;
    }
    public Vendedor findByUser(String user){
        Vendedor vendedor = repo.findByUser(user);
        
        if(vendedor.equals(null)){
            throw new NotFoundException("Vendedor não encontrado.");
        }
        
        return vendedor;
    }
    public Vendedor save(Vendedor a){
       try{
           verificaUser(a.getUser());
           a.setSenha(new BCryptPasswordEncoder().encode(a.getSenha()));
           return repo.save(a);
           
       }catch (Exception e) {
            Throwable t = e;
            while (t.getCause() != null) {
                t = t.getCause();
                if (t instanceof ConstraintViolationException) {
                    throw ((ConstraintViolationException) t);
                }
            }
            throw new RuntimeException("Falha ao salvar a Vendedor.");
        }
        
    }
    public Vendedor update(Vendedor a){
       Vendedor obj = findById(a.getId());
        
       try{
           a.setCnpj(obj.getCnpj());
           return repo.save(a);
        }catch(Exception e){
            Throwable t = e;
            while(t.getCause() != null){
                t = t.getCause();
                if( t instanceof ConstraintViolationException){
                    throw ((ConstraintViolationException) t);
                }
            }
            throw new RuntimeException("Falha ao atualizar o Vendedor.");
        }
        
    }
    
    public Vendedor updateSenha(Vendedor a, String senhaAtual, String novaSenha, String confirmarNovaSenha){
        Vendedor obj = findById(a.getId());
        
        alterarSenha(obj,senhaAtual,novaSenha,confirmarNovaSenha);
       try{
           a.setCnpj(obj.getCnpj());
           return repo.save(a);
        }catch(Exception e){
            Throwable t = e;
            while(t.getCause() != null){
                t = t.getCause();
                if( t instanceof ConstraintViolationException){
                    throw ((ConstraintViolationException) t);
                }
            }
            throw new RuntimeException("Falha ao atualizar o Vendedor.");
        }
        
    }
    
    public void delete(Long id){
        Vendedor obj = findById(id);
        try{
           repo.delete(obj);
        }catch(Exception e){
           throw new RuntimeException("Falha ao excluir o Vendedor.");
        }
    }
    
    private void alterarSenha(Vendedor obj, String senhaAtual, String novaSenha, String confirmarNovaSenha){
        if(!senhaAtual.isBlank() && !novaSenha.isBlank() && !confirmarNovaSenha.isBlank()){
            if(!senhaAtual.equals(obj.getSenha())){
                throw new RuntimeException("Senha Atual está incorreta.");
            }
            if(!novaSenha.equals(confirmarNovaSenha)){
                throw new RuntimeException("Nova senha e a senha de confirmação são diferentes.");
            }
            obj.setSenha(new BCryptPasswordEncoder().encode(novaSenha));
        }
    }

    private void verificaUser(String user) {
        Vendedor result = repo.findByUser(user);
        Usuario resulte = repoo.findByUser(user);
        Administrador resultado = repooo.findByUser(user);
        
        if (result != null||resulte != null || resultado != null) {
            throw new RuntimeException("Username já cadastrado.");
        }
    }
}
