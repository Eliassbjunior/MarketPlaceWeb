
package br.edu.iff.marketplace.security;



import br.edu.iff.marketplace.model.Administrador;
import br.edu.iff.marketplace.model.Usuario;
import br.edu.iff.marketplace.model.Vendedor;
import br.edu.iff.marketplace.repository.UsuarioRepository;
import br.edu.iff.marketplace.repository.VendedorRepository;
import br.edu.iff.marketplace.model.Permissao;
import br.edu.iff.marketplace.repository.AdministradorRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PessoaDetailsService implements UserDetailsService {
    @Autowired
    private VendedorRepository repo;
    
    @Autowired
    private UsuarioRepository repoo;
    
    @Autowired
    private AdministradorRepository repooo;
    
    
    
    @Override
    public UserDetails loadUserByUsername(String user) throws UsernameNotFoundException {
       Usuario usuario = repoo.findByUser(user);
       Vendedor vendedor = repo.findByUser(user);
       Administrador administrador = repooo.findByUser(user);
       
       if(usuario == null && vendedor == null && administrador == null){
           throw new UsernameNotFoundException("Username não encontrado: "+ user);
       }
       if(vendedor == null){
           if(administrador == null){
                return new User(usuario.getUser(),usuario.getSenha(), getAuthorities(usuario.getPermissoes()));
           }else{
               return new User(administrador.getUser(),administrador.getSenha(), getAuthorities(administrador.getPermissoes()));
           }
       }else{
           return new User(vendedor.getUser(),vendedor.getSenha(), getAuthorities(vendedor.getPermissoes()));
       }
       
    }
    
    private List<GrantedAuthority> getAuthorities(List<Permissao> lista){
        List<GrantedAuthority>l = new ArrayList<>();
        
        for(Permissao p : lista){
            l.add(new SimpleGrantedAuthority("ROLE_"+p.getNome()));
        }
        return l;
    }
}
