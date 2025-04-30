package com.coder.ecommerce.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
//Definimos la tabla

@Table(name = "invoice_details")
@NoArgsConstructor
public class InvoiceDetails {
    public InvoiceDetails(int amount, double price){
        this.amount = amount;
        this.price = price;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    @Column(name = "amount", nullable = false)
    public int amount;
    @Column(name = "price", nullable = false)
    public double price;

    //Definimos las relaciones

    @ManyToOne(fetch=FetchType.LAZY)
    private Products product;

    @ManyToOne(fetch=FetchType.LAZY)
    private Invoice invoice;

}

