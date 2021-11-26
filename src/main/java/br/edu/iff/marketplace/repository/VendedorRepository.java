/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.iff.marketplace.repository;

import br.edu.iff.marketplace.model.Vendedor;
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
public interface VendedorRepository extends JpaRepository<Vendedor, Long>{
    @Query("SELECT v FROM Vendedor v WHERE v.nome LIKE :nome OR v.user = :user")
    public List<Vendedor> findByNome(@Param("nome")String nome, @Param("user") String user);
    
    @Query("SELECT v FROM Vendedor v WHERE v.user = :user")
    public Vendedor findByUser(@Param("user") String user);

}
