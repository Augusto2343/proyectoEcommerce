package com.coder.ecommerce.controllers;

import com.coder.ecommerce.entities.Products;
import com.coder.ecommerce.services.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    private ProductServices productService;

    @GetMapping
    public ResponseEntity<List<Products>> findAll(){
        return ResponseEntity.ok(this.productService.findAll());
    }
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Products> save(@RequestBody Products products){
        try{
            Products newProducts = this.productService.save(products);
            return ResponseEntity.created(URI.create("/products/"+ newProducts.getId())).body(newProducts);
        }catch(Exception error){
            System.out.println(error.getMessage());
            return ResponseEntity.badRequest().build();
        }

    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Products>> update(@PathVariable Long id,@RequestBody Products product){
        Optional<Products> productOptional = this.productService.update(id, product);
        if(productOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productOptional);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Optional<Products>> delete(@PathVariable Long id){
        Optional<Products> productOptional= this.productService.delete(id);
        if(productOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productOptional);
    }

}
