package com.coder.ecommerce.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
//Definimos la tabla
@NoArgsConstructor
@Table(name = "CLIENT")

public class Client {

    @Id
    @JsonProperty(access= JsonProperty.Access.READ_ONLY)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column( nullable = false)
    private String name;

    @Column( nullable = false)
    private String lastname;

    @Column( nullable = false)
    private Long docnumber;

    @Schema( accessMode = Schema.AccessMode.READ_ONLY)
    private List<Long> invoiceIds= new ArrayList<>();

    @JsonIgnore
    @Schema( hidden = true)
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Invoice> invoices = new ArrayList<>();
}


