package br.edu.iff.marketplace;


import br.edu.iff.marketplace.model.Administrador;
import br.edu.iff.marketplace.model.Endereco;

import br.edu.iff.marketplace.model.Produto;
import br.edu.iff.marketplace.model.Usuario;
import br.edu.iff.marketplace.model.Venda;
import br.edu.iff.marketplace.model.Vendedor;
import br.edu.iff.marketplace.repository.PermissaoRepository;


import br.edu.iff.marketplace.repository.ProdutoRepository;
import br.edu.iff.marketplace.repository.UsuarioRepository;
import br.edu.iff.marketplace.repository.VendaRepository;
import br.edu.iff.marketplace.repository.VendedorRepository;
import br.edu.iff.marketplace.model.Permissao;
import br.edu.iff.marketplace.repository.AdministradorRepository;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



@SpringBootApplication
public class MarketplaceApplication implements CommandLineRunner{
       
    @Autowired
    private PermissaoRepository permissaorepo;
    
    @Autowired
    private AdministradorRepository adminrepo;
    
	public static void main(String[] args) {
		SpringApplication.run(MarketplaceApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        Endereco end = new Endereco();
        
        Calendar nascimento = Calendar.getInstance();
        nascimento.set(1993, 3, 14);
        
        Permissao pUsuario = new Permissao();
        pUsuario.setNome("USUARIO");
        
        Permissao pVendedor = new Permissao();
        pVendedor.setNome("VENDEDOR");
        
        Permissao pAdmin = new Permissao();
        pAdmin.setNome("ADMIN");
        permissaorepo.saveAll(List.of(pUsuario, pVendedor,pAdmin));
        
     
        Administrador admin = new Administrador();
        
        end.setCep("13501-450");
        end.setBairro("Estádio");
        end.setCidade("Rio Claro");
        end.setEstado("São Paulo");
        end.setRua("Avenida 35");
        end.setNumero(35);
        
        admin.setEndereco(end);
        admin.setNome("Administrador");
        admin.setSenha(new BCryptPasswordEncoder().encode("admin"));
        admin.setUser("root");
        admin.setCpf("428.322.870-37");
        admin.setDataDeNascimento(nascimento);
        admin.setTelefone("22998765432");
        admin.setPermissoes(List.of(pAdmin));
          
        adminrepo.save(admin);
        
    }

}
