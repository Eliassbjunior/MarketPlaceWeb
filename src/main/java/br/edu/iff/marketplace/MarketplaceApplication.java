package br.edu.iff.marketplace;


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
    private UsuarioRepository usuariorepo;
    
    @Autowired
    private VendedorRepository vendedorrepo;
    
    @Autowired
    private PermissaoRepository permissaorepo;
    
    @Autowired
    private VendaRepository vendarepo;
    
    @Autowired
    private ProdutoRepository produtorepo;
    
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
        
        permissaorepo.saveAll(List.of(pUsuario, pVendedor));
        
     
        
        end.setCep("13501-450");
        end.setBairro("Estádio");
        end.setCidade("Rio Claro");
        end.setEstado("São Paulo");
        end.setRua("Avenida 35");
        end.setNumero(35);
        
        Usuario usuario = new Usuario();
        usuario.setNome("Carn");
        usuario.setCpf("428.302.870-37");
        usuario.setUser("Eliasjunior_");
        usuario.setSenha(new BCryptPasswordEncoder().encode("130798"));
        usuario.setDataDeNascimento(nascimento);
        usuario.setEndereco(end);
        usuario.setTelefone("22998474816");
        usuario.setPermissoes(List.of(pUsuario));
        
        
        Vendedor vendedor = new Vendedor();
    
        
        vendedor.setNome("Ertiu");
        vendedor.setCnpj("11.393.561/0001-14");
        vendedor.setUser("Eliassbjunior");
        vendedor.setSenha(new BCryptPasswordEncoder().encode("130798"));
        vendedor.setEndereco(end);
        vendedor.setDataDeNascimento(nascimento);
        vendedor.setTelefone("22998474816");
        vendedor.setPermissoes(List.of(pVendedor));
        
        Produto produto = new Produto();
        
        produto.setNome("Violao");
        produto.setPreco(813.75);
        produto.setVendedor(vendedor);
        produto.setDescricao("Modelo: SA200C\n" +
"Tipo: Eletroacústico\n" +
"Cor: Mahogany\n" +
"Estilo do corpo: mini jumbo\n" +
"Número de cordas: 6\n" +
"Tipo de cordas: Aço\n" +
"Madeira do tampo: Spruce\n" +
"Madeira do Fundo e Lateral: Sapele\n" +
"Escala: Blackwood\n" +
"Tarraxas: Blindada Cromadas\n" +
"Pré: SE50\n" +
"Estilo: Fosco\n" +
"Entrada: P10\n" +
"Fabricação: China\n" +
"Garantia: 03 meses");
        Produto produto2 = new Produto();
        produto2.setNome("Carro");
        produto2.setPreco(30.000);
        produto2.setVendedor(vendedor);
        produto2.setDescricao("Volkswagen Gol");
        vendedor.setProdutos(List.of(produto));
        vendedor.setProdutos(List.of(produto2));
        
        usuariorepo.save(usuario);
        vendedorrepo.save(vendedor);
        produtorepo.save(produto);
        
        Venda venda = new Venda();
        venda.setProduto(produto);
        venda.setUsuario(usuario);
        venda.setVendedor(vendedor);
        venda.setEndereco(usuario.getEndereco());
        
        vendarepo.save(venda);
    }

}
