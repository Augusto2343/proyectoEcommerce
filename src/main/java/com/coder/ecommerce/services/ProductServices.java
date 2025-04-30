package com.coder.ecommerce.services;


import com.coder.ecommerce.entities.Products;
import com.coder.ecommerce.repositories.ProductsRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServices {
        @Autowired
        private ProductsRepositories productsRepository;
        public Products save(Products product){
            return this.productsRepository.save(product);
            }
        public List<Products> findAll(){return this.productsRepository.findAll();}
        public Optional<Products> update(Long id, Products product){
            Optional<Products> productOptional = this.productsRepository.findById(id);
            if(productOptional.isEmpty()){
                return Optional.empty();
                }
            productOptional.get().setDescription(product.getDescription());
            productOptional.get().setCode(product.getCode());
            productOptional.get().setPrice(product.getPrice());
            productOptional.get().setStock(product.getStock());
            this.productsRepository.save(productOptional.get());
            return productOptional;
        }
        public Optional<Products> delete(Long id){
            Optional<Products> productOptional = this.productsRepository.findById(id);
            if(productOptional.isEmpty()){
                return Optional.empty();
                }
            this.productsRepository.delete(productOptional.get());
            return productOptional;
            }
        }


