/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.iff.marketplace.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Column;
import javax.persistence.Entity;

/**
 *
 * @author elias
 */
@Entity
@JsonIgnoreProperties(value = {"senha","nivelDeAcesso"}, allowGetters = false, allowSetters = true)
public class Administrador extends Pessoa{
    @Column(length = 14,unique = true, updatable = false)
    private String cpf;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Administrador() {
        this.nivelDeAcesso=2;
    }
    
    
}
