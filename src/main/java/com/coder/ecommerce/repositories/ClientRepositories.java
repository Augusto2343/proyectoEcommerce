package com.coder.ecommerce.repositories;

import com.coder.ecommerce.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ClientRepositories extends JpaRepository<Client, Long> {
}
