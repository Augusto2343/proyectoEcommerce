package com.coder.ecommerce.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
//Definimos la tabla

@Table(name = "INVOICE_DETAILS")

public class InvoiceDetails {
    public InvoiceDetails(int amount, double price){
        this.amount = amount;
        this.price = price;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    @Column(name = "AMOUNT", nullable = false)
    public int amount;
    @Column(name = "PRICE", nullable = false)
    public double price;

    //Definimos las relaciones

    @ManyToOne(fetch=FetchType.LAZY)
    private Products product;

    @ManyToOne(fetch=FetchType.LAZY)
    private Invoice invoice;

}

