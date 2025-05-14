package com.coder.ecommerce.entities;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column( nullable = false)
    private String name;
    @Column( nullable = false)
    private String lastname;
    @Column( nullable = false)
    private Long docnumber;
    @Column(nullable = false)
    private List<Long> invoiceIds= new ArrayList<>();
    @OneToMany(mappedBy = "client_id", cascade = CascadeType.ALL)
    private List<Invoice> invoices = new ArrayList<>();
}


