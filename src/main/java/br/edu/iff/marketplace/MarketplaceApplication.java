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
	private VendedorRepository vendedorrepo;
	
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
        end.setBairro("Est??dio");
        end.setCidade("Rio Claro");
        end.setEstado("S??o Paulo");
        end.setRua("Avenida 35");
        end.setNumero(35);
        
	Vendedor vendedor = new Vendedor();
	vendedor.setEndereco(end);
	vendedor.setNome("AdministradorNome");
        vendedor.setSenha(new BCryptPasswordEncoder().encode("admin"));
        vendedor.setUser("administrador");
        vendedor.setCnpj("94.042.285/0001-77");
        vendedor.setDataDeNascimento(nascimento);
        vendedor.setTelefone("22998765432");
        vendedor.setPermissoes(List.of(pVendedor));  
	vendedorrepo.save(vendedor);    
	    
        admin.setEndereco(end);
        admin.setNome("AdministradorNome");
        admin.setSenha(new BCryptPasswordEncoder().encode("admin"));
        admin.setUser("Eliassbjunior");
        admin.setCpf("94.042.285/0001-77");
        admin.setDataDeNascimento(nascimento);
        admin.setTelefone("22998765432");
        admin.setPermissoes(List.of(pAdmin));
          
        //adminrepo.save(admin);
        
    }

}
