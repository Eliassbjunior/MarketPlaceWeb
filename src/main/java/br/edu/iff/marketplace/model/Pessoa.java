package br.edu.iff.marketplace.model;

import br.edu.iff.marketplace.annotation.TelefoneValidation;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;


/**
 *
 * @author elias
 */

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pessoa implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    
    @NotBlank(message = "Nome é obrigatório.")
    @Column(length = 40, nullable = false)
    protected String nome;
    
    @NotBlank(message = "User é obrigatório.")
    @Column(unique = true,length = 20, nullable = false, updatable = false)
    protected String user;
    
    @NotBlank(message = "Senha é obrigatório.")
    @Column( nullable = false)
    protected String senha;
    
    @Column(updatable = false)
    protected int nivelDeAcesso;
    
    @NotNull(message = "Data de Nascimento é obrigatório.")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    protected Calendar dataDeNascimento;
    
    @NotBlank(message = "Telefone é obrigatório.")
    @Length(max = 15,min = 11)
    @Column(length = 15, nullable = false)
    @TelefoneValidation
    protected String telefone;
    
    @NotNull(message = "Endereço é obrigatório.")
    @Embedded
    @Valid
    protected Endereco endereco;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @OrderColumn
    @Size(min = 1, message = "Pessoa deve ter no minimo 1 permissão.")
    private List<Permissao> permissoes;

    public List<Permissao> getPermissoes() {
        return permissoes;
    }

    public Pessoa() {
        this.permissoes = new ArrayList<>();
        
    }

    public void setPermissoes(List<Permissao> permissoes) {
        this.permissoes = permissoes;
    }

    
    
    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final Pessoa other = (Pessoa) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getNivelDeAcesso() {
        return nivelDeAcesso;
    }

    public void setNivelDeAcesso(int nivelDeAcesso) {
        this.nivelDeAcesso = nivelDeAcesso;
    }

    public Calendar getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento(Calendar dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
    
}

/*# id: Long; 
# nome: String
# user: String
# senha: String
# nivelDeAcesso: int
# dataDeNascimento: Calendar
# telefone: String
# cep: String
# bairro: String
# rua: String
# numero: int
# cidade: String*/
