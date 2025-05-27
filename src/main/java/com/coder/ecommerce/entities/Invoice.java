package com.coder.ecommerce.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Data
@Entity
//Definimos la tabla
@NoArgsConstructor
@Table(name = "INVOICE")

public class Invoice {
    @Id
    @JsonProperty(access= JsonProperty.Access.READ_ONLY)

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @JsonProperty(access= JsonProperty.Access.READ_ONLY)
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date createdAt;

    @JsonProperty(access= JsonProperty.Access.READ_ONLY)
    private double total;

    @Column(nullable = false)
    private Long idDelCliente;

    @Column(nullable = false)
    private List<Long> idInvoiceDetails = new ArrayList<>();


    @JsonIgnore
    @Schema( hidden = true)
    @ManyToOne(fetch = FetchType.LAZY)
    private Client client;


    @JsonIgnore
    @Schema( hidden = true)
    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
    private List<InvoiceDetails> invoiceDetails = new ArrayList<>();


}
