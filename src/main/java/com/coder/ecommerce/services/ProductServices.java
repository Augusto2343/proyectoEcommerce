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
        public Products save(Products product){return this.productsRepository.save(product);}

        public List<Products> findAll(){return this.productsRepository.findAll();}

        public Products findById(Long id){ return productsRepository.findById(id).orElse(null);}

        public Products update(Long id, Products product){
            Products products = productsRepository.findById(id).orElse(null);
            if(products==null){
                return null;
                }
            products.setDescription(products.getDescription());
            products.setCode(products.getCode());
            products.setPrice(products.getPrice());
            products.setStock(products.getStock());
            this.productsRepository.save(products);
            return products;
        }
        public Products delete(Long id){
            Products productos = productsRepository.findById(id).orElse(null);
            if(productos == null){
                return null;
                }
            this.productsRepository.delete(productos);
            return productos;
            }
        }


