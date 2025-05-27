package com.coder.ecommerce.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @JsonProperty(access= JsonProperty.Access.READ_ONLY)
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

    @JsonProperty(access= JsonProperty.Access.READ_ONLY)
    private List <Long> invoiceDetailsIds = new ArrayList<>();
    @JsonIgnore
    @Schema( hidden = true)
    @OneToMany(mappedBy = "products", cascade = CascadeType.ALL)
    private List<InvoiceDetails> invoiceDetails = new ArrayList<>();
}
