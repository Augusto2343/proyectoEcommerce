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
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    private ProductServices productService;

    @GetMapping
    public ResponseEntity<List<Products>> findAll(){
        return ResponseEntity.ok(productService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Products> findById(@PathVariable Long id){
        return ResponseEntity.ok(productService.findById(id));
    }
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Products> save(@RequestBody Products products){
        return ResponseEntity.ok(productService.save(products));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Products> update(@PathVariable Long id,@RequestBody Products product){
        return ResponseEntity.ok(productService.update(id, product));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity <Products> delete(@PathVariable Long id){
        return ResponseEntity.ok(productService.delete(id));
    }

}
