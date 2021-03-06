/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.iff.marketplace.controller.apirest;
import br.edu.iff.marketplace.model.Produto;
import br.edu.iff.marketplace.service.ProdutoService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/apirest/marketplace/produtos")
public class ProdutoController {
    @Autowired
    private ProdutoService service;
    
    @GetMapping
    public ResponseEntity getAll(
        @RequestParam(name = "page", defaultValue = "0", required = false) int page,
        @RequestParam(name = "size", defaultValue = "10", required = false) int size){        
        
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll(page, size));
    }
    
    @GetMapping(path = "/busca?={nome}")
    public ResponseEntity getOne(@PathVariable("nome") String nome){
        return ResponseEntity.ok(service.findByNome(nome));
    }
    
   @GetMapping(path = "/{id}")
    public ResponseEntity getProdutoId(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.findById(id));
    }
    
    
    @PostMapping
    public ResponseEntity save(@Valid @RequestBody Produto produto){
        produto.setId(null);
        service.save(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produto);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @Valid @RequestBody Produto produto){
        produto.setId(id);
   
        service.update(produto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
     
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
