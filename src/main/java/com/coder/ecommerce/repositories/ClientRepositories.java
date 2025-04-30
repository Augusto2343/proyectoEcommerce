package com.coder.ecommerce.repositories;

import com.coder.ecommerce.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepositories extends JpaRepository<Client, Long> {

}
