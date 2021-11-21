/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.iff.marketplace.model;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author elias
 */
@Embeddable
public class Endereco {
    
    @Column(length = 9)
    @Length(min = 8, max = 9, message = "Quantidade de números inválida")
    @NotBlank(message = "Nome é obrigatório.")
    private String cep;
    
    @Length( max = 50, message = "Limite de caracteres excedido.")
    @NotBlank(message = "Nome é obrigatório.")
    @Column(length = 50, nullable = false)
    private String bairro;
    
    
    @NotBlank(message = "Nome é obrigatório.")
    @Length( max = 50, message = "Limite de caracteres excedido.")
    @Column(length = 50, nullable = false)
    private String rua;
    
    @Digits(integer = 4, fraction = 0, message = "Numero tem que ser inteiro.")
    @Column()
    private int numero;
    
    @Column(length = 50, nullable = false)
    @Length( max = 50, message = "Limite de caracteres excedido.")
    @NotBlank(message = "Nome é obrigatório.")
    private String cidade;
    
    
    @NotBlank(message = "Nome é obrigatório.")
    @Length( max = 50, message = "Limite de caracteres excedido.")
    @Column(length = 50, nullable = false)
    private String estado;

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + Objects.hashCode(this.cep);
        hash = 19 * hash + Objects.hashCode(this.bairro);
        hash = 19 * hash + Objects.hashCode(this.rua);
        hash = 19 * hash + this.numero;
        hash = 19 * hash + Objects.hashCode(this.cidade);
        hash = 19 * hash + Objects.hashCode(this.estado);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Endereco other = (Endereco) obj;
        if (this.numero != other.numero) {
            return false;
        }
        if (!Objects.equals(this.cep, other.cep)) {
            return false;
        }
        if (!Objects.equals(this.bairro, other.bairro)) {
            return false;
        }
        if (!Objects.equals(this.rua, other.rua)) {
            return false;
        }
        if (!Objects.equals(this.cidade, other.cidade)) {
            return false;
        }
        if (!Objects.equals(this.estado, other.estado)) {
            return false;
        }
        return true;
    }

    public String getCep() {
        return cep;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
    
}
