/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.iff.marketplace.model;
import br.edu.iff.marketplace.model.Permissao;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OrderColumn;
import javax.validation.constraints.Size;

/**
 *
 * @author elias
 */
@Entity
@JsonIgnoreProperties(value = "senha", allowGetters = false, allowSetters = true)
public class Usuario extends Pessoa{
    @Column(length = 14,unique = true, updatable = false)
    private String cpf;
    
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    
    public Usuario() {
        this.nivelDeAcesso=0;
        Permissao pUsuario = new Permissao();
        pUsuario.setNome("USUARIO");
        this.setPermissoes(List.of(pUsuario));
    }
    

}
