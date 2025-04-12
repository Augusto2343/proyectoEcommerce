package com.coder.ecommerce.entities;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "CLIENT")

public class Client {
    public Client(String name, String lastname, long docnumber) {
        this.name = name;
        this.lastname = lastname;
        this.docnumber = docnumber;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "NAME", nullable = false)
    private String name;
    @Column(name = "LASTNAME", nullable = false)
    private String lastname;
    @Column(name = "DOCNUMBER", nullable = false)
    private long docnumber;


    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Invoice> invoice;
}

