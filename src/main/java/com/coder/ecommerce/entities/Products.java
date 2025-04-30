package com.coder.ecommerce.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
//Definimos la tabla
@NoArgsConstructor
@Table(name = "PRODUCTS")
public class Products {
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
    
    //Definimos las relaciones

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    private List<InvoiceDetails> invoice_details;
}
