package com.coder.ecommerce.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(readOnly = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( nullable = false)
    private int amount;

    @Schema(readOnly = true)
    private Double price;

    @Column(nullable = false)
    private Long idProducto;

    @Schema( accessMode = Schema.AccessMode.READ_ONLY)
    private List<Long> invoiceIds = new ArrayList<>();

    @JsonIgnore
    @Schema( hidden = true)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = true)
    private Products products;

    @JsonIgnore
    @Schema( hidden = true)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice", nullable = true)
    private Invoice invoice;
    }

