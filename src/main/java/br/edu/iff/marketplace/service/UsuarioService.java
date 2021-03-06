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
import java.util.Calendar;
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
public class UsuarioService {
    @Autowired
    private UsuarioRepository repo;
    
    @Autowired
    private VendedorRepository repoo;
    
    @Autowired
    private AdministradorRepository repooo;
    
    
    
    public List<Usuario>findAll(int page, int size){
        Pageable p = PageRequest.of(page, size);
        return repo.findAll(p).toList();
            
    }
    
    public List<Usuario> findAll(){
        return repo.findAll();
    }
    public List<Usuario> findByNome(String user){
        String nome = user;
        List<Usuario> result = repo.findByUsuario('%'+nome+'%',user);
        if(result.isEmpty()){
            throw new NotFoundException("Usuario não encontrado.");
        }
        
        return result;
    }
    
    public Usuario findByUser(String user){
        Usuario usuario = repo.findByUser(user);
        
        if(usuario.equals(null)){
            throw new NotFoundException("Usuario não encontrado.");
        }
        
        return usuario;
    }
    
    public Usuario findById(Long id){
        Optional<Usuario> result = repo.findById(id);
        if(result.isEmpty()){
            throw new NotFoundException("Usuario não encontrado.");
        }
        
        return result.get();
    }
    
    public Usuario save(Usuario a){
        
       try {
            verificaUser(a.getUser());
            
            a.setSenha(new BCryptPasswordEncoder().encode(a.getSenha()));
            return repo.save(a);
            
        } catch (Exception e) {
            Throwable t = e;
            while (t.getCause() != null) {
                t = t.getCause();
                if (t instanceof ConstraintViolationException) {
                    throw ((ConstraintViolationException) t);
                }
            }
            throw new RuntimeException("Falha ao salvar a Usuario.");
        }
        
    }
    
    public Usuario update(Usuario a){
        Usuario obj = findById(a.getId());
  
       try{
           a.setCpf(obj.getCpf());
           return repo.save(a);
        }catch(Exception e){
            Throwable t = e;
            while(t.getCause() != null){
                t = t.getCause();
                if( t instanceof ConstraintViolationException){
                    throw ((ConstraintViolationException) t);
                }
            }
            throw new RuntimeException("Falha ao atualizar o Usuario.");
        }
        
    }
    
    public Usuario updateSenha(Usuario a, String senhaAtual, String novaSenha, String confirmarNovaSenha){
        Usuario obj = findById(a.getId());
        
        alterarSenha(obj,senhaAtual,novaSenha,confirmarNovaSenha);
       try{
           a.setCpf(obj.getCpf());
           return repo.save(a);
        }catch(Exception e){
            Throwable t = e;
            while(t.getCause() != null){
                t = t.getCause();
                if( t instanceof ConstraintViolationException){
                    throw ((ConstraintViolationException) t);
                }
            }
            throw new RuntimeException("Falha ao atualizar o Usuario.");
        }
        
    }
    
    public void delete(Long id){
        Usuario obj = findById(id);
        try{
           repo.delete(obj);
        }catch(Exception e){
           throw new RuntimeException("Falha ao excluir o Usuario.");
        }
    }
    
    private void alterarSenha(Usuario obj, String senhaAtual, String novaSenha, String confirmarNovaSenha){
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
        Usuario result = repo.findByUser(user);
        Vendedor resulte = repoo.findByUser(user);
        Administrador resultado = repooo.findByUser(user);
        if (result != null||resulte != (null)||resultado != null) {
            throw new RuntimeException("Username já cadastrado.");
        }
    }

}

    