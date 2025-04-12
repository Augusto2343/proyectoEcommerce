package com.coder.ecommerce.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "PRODUCTS")
public class Products {
    public Products(String description, String code, double price, int stock) {
        this.description = description;
        this.code = code;
        this.price = price;
        this.stock = stock;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "DESCRIPTION", nullable = false)
    private String description;
    @Column(name = "CODE", nullable = false)
    private String code;
    @Column(name = "PRICE", nullable = false)
    private double price;
    @Column(name = "STOCK", nullable = false)
    private int stock;

    @OneToMany(mappedBy = "product")
    private List<InvoiceDetails> invoice_details;
}
