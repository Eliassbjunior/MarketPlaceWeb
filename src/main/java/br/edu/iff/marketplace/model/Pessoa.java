package br.edu.iff.marketplace.model;

import br.edu.iff.marketplace.annotation.TelefoneValidation;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;


/**
 *
 * @author elias
 */

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Pessoa implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    protected Long id;
    
    @NotBlank(message = "Nome é obrigatório.")
    @Column(length = 40, nullable = false)
    protected String nome;
    
    @NotBlank(message = "User é obrigatório.")
    @Column(unique = true,length = 20, nullable = false, updatable = false)
    protected String user;
    
    @NotBlank(message = "Senha é obrigatório.")
    @Column(length = 40, nullable = false)
    protected String senha;
    
    @Column(nullable = false, updatable = false)
    protected int nivelDeAcesso;
    
    @NotNull(message = "Data de Nascimento é obrigatório.")
    @Temporal(TemporalType.DATE)
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