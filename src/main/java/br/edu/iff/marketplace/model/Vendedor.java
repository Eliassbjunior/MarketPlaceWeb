/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.iff.marketplace.model;
import br.edu.iff.marketplace.model.Permissao;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.validation.constraints.Size;

/**
 *
 * @author elias
 */
@Entity
@JsonIgnoreProperties(value = {"senha","nivelDeAcesso"}, allowGetters = false, allowSetters = true)
public class Vendedor extends Pessoa{
    @Column(length = 18,unique = true, updatable = false)
    private String cnpj;
    
    @JsonIgnore
    @OneToMany(mappedBy = "vendedor")
    private List<Venda> vendas = new ArrayList<>();
    
    @JsonIgnore
    @OneToMany(mappedBy = "vendedor",cascade = CascadeType.ALL)
    private List<Produto> produtos = new ArrayList<>();

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public List<Venda> getVendas() {
        return vendas;
    }

    public void setVendas(List<Venda> vendas) {
        this.vendas = vendas;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }
    
    
    public Vendedor() {
        this.nivelDeAcesso = 1;
    }
    
  
}
