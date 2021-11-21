package br.edu.iff.marketplace.controller.apirest;

import br.edu.iff.marketplace.model.Vendedor;
import br.edu.iff.marketplace.service.VendedorService;
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
@RequestMapping(path = "/apirest/marketplace/vendedores")
public class VendedorController {
    
    @Autowired 
    private VendedorService service;
    
    @GetMapping
    public ResponseEntity getAll(
        @RequestParam(name = "page", defaultValue = "0", required = false) int page,
        @RequestParam(name = "size", defaultValue = "10", required = false) int size){
        
        return ResponseEntity.ok(service.findAll(page, size));
    }
    
    @GetMapping(path = "/{id}")
    public ResponseEntity getVendedorId(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.findById(id));
    }
    
    @GetMapping(path = "/busca?={vendedor}")
    public ResponseEntity getVendedor(@RequestParam("vendedor") String user){
        return ResponseEntity.ok(service.findByNome(user));
    }
    
    //É preciso criar o ReponseEntity para listar todas as vendas feitas por um vendedor.
    
    @PostMapping
    public ResponseEntity save(@Valid @RequestBody Vendedor vendedor){
        vendedor.setId(null);
        service.save(vendedor);
        return ResponseEntity.status(HttpStatus.CREATED).body(vendedor);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @Valid @RequestBody Vendedor vendedor){
        vendedor.setId(id);
        
        /*Tive que criar um novo update no service pois na hora de fazer o update ele já pedia direto para trocar
        a senha*/
        service.update(vendedor);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @PutMapping("/{id}/alterarSenha")
    public ResponseEntity updateSenha(@PathVariable("id") Long id,
    @RequestParam(name = "senhaAtual", defaultValue = "", required = true) String senhaAtual,
    @RequestParam(name = "novaSenha", defaultValue = "", required = true) String novaSenha,
    @RequestParam(name = "confirmarNovaSenha", defaultValue = "", required = true) String confirmarNovaSenha){
        
    Vendedor v = service.findById(id);
    service.updateSenha(v, senhaAtual, novaSenha, confirmarNovaSenha);
    
    
    return ResponseEntity.ok().build();
    }
    
    
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
