
package br.edu.iff.marketplace.repository;


import br.edu.iff.marketplace.model.Permissao;
import br.edu.iff.marketplace.model.Produto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissaoRepository extends JpaRepository<Permissao, Long> {
    @Query("SELECT p FROM Permissao p WHERE p.nome = :nome")
    public Permissao findByNome(@Param("nome") String nome);
    
}
