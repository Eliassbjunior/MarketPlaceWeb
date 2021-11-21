/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.iff.marketplace.repository;

import br.edu.iff.marketplace.model.Administrador;
import br.edu.iff.marketplace.model.Usuario;
import br.edu.iff.marketplace.model.Vendedor;
import java.util.List;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author elias
 */
@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, Long> {
    @Query("SELECT u FROM Usuario u WHERE u.user = :user")
    public List<Usuario> findByUsuario(@Param("user") String user);
    
    @Query("SELECT v FROM Vendedor v WHERE v.user = :user")
    public List<Vendedor> findByVendedor(@Param("user")String user);

    @Query("SELECT a FROM Administrador a WHERE a.user = :user")
    public List<Administrador> findByUser(@Param("user") String user);

   
    
}
