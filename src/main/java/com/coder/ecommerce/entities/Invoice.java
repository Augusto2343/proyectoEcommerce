package com.coder.ecommerce.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
//Definimos la tabla

@Table(name = "INVOICE")

public class Invoice {
    public Invoice (String createdAt, double total){
        this.createdAt = createdAt;
        this.total = total;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "CREATED_AT", nullable = false)
    private String createdAt;
    @Column(name = "TOTAL", nullable = false)
    private double total;

    //Definimos las relaciones
    @ManyToOne(fetch=FetchType.LAZY)
    private Client client;
    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<InvoiceDetails> invoiceDetails;
}
