package com.coder.ecommerce.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
//Definimos la tabla
@NoArgsConstructor
@Table(name = "PRODUCTS")
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column( nullable = false)
    private String description;
    @Column( nullable = false)
    private String code;
    @Column( nullable = false)
    private double price;
    @Column( nullable = false)
    private int stock;
    @OneToMany(mappedBy = "products", cascade = CascadeType.ALL)
    private List<InvoiceDetails> invoiceDetails = new ArrayList<>();
}
