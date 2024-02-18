package com.home.client.repository;

import com.home.client.model.Client;
import com.home.client.model.ClientId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, ClientId> {

}
