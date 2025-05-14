package com.coder.ecommerce.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
//Definimos la tabla

@Table(name = "invoice_details")
@NoArgsConstructor
public class InvoiceDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column( nullable = false)
    private int amount;

    private double price;

    @Column(nullable = false)
    private Long idProducto;
    @OneToMany(mappedBy = "invoice_details_id", cascade = CascadeType.ALL)
    private List<Invoice> invoices = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_product", nullable = false)
    private Products products;
    }

