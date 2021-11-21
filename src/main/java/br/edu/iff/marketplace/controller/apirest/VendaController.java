package br.edu.iff.marketplace.controller.apirest;

import br.edu.iff.marketplace.model.Venda;
import br.edu.iff.marketplace.service.VendaService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/apirest/marketplace/venda")
public class VendaController {
    @Autowired 
    private VendaService service;
    
    /*A intenção de colocar um path dentro desse GetMapping é para só listar todas as vendas ocorridas caso seja
    utilizado o caminho "marketplace/Venda/Lista", não preciso que essa lista automática seja gerada quando se
    entra no controller de venda.
    */
    @GetMapping(path = "/lista")
    public ResponseEntity getAll(
        @RequestParam(name = "page", defaultValue = "0", required = false) int page,
        @RequestParam(name = "size", defaultValue = "10", required = false) int size){
        
        return ResponseEntity.ok(service.findAll(page, size));
    }
    
    @PostMapping
    public ResponseEntity save(@Valid @RequestBody Venda venda){
        venda.setId(null);
        service.save(venda);
        return ResponseEntity.status(HttpStatus.CREATED).body(venda);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
