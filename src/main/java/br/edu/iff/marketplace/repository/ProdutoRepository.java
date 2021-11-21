/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.iff.marketplace.repository;

import br.edu.iff.marketplace.model.Produto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author elias
 */
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Object>{
    @Query("SELECT p FROM Produto p WHERE p.nome = :nome")
    public List<Produto> findByNome(@Param("nome") String nome);
}
